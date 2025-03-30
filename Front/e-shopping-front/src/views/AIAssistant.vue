<template>
  <div class="home-container" :class="{ 'dark-mode': isDarkMode }">
    <!-- 背景动画效果 -->
    <div class="background-shapes">
      <div class="shape shape-1"></div>
      <div class="shape shape-2"></div>
      <div class="shape shape-3"></div>
    </div>

    <!-- 左侧导航栏 -->
    <aside class="sidebar" :class="{ 'collapsed': isSidebarCollapsed }">
      <div class="sidebar-header">
        <div class="brand-container">
          <h1 class="brand-name">ES-Shopping</h1>
          <div class="sidebar-toggle" @click="toggleSidebar">
            <span class="material-icons">{{ isSidebarCollapsed ? 'menu' : 'menu_open' }}</span>
          </div>
        </div>
      </div>

      <nav class="sidebar-nav">
        <div 
          v-for="(item, index) in navItems" 
          :key="index" 
          class="nav-item"
          :class="{ 'active': activeNavIndex === index }"
          @click="setActiveNav(index)"
        >
          <span class="material-icons">{{ item.icon }}</span>
          <span class="nav-title" v-show="!isSidebarCollapsed">{{ item.title }}</span>
          <div class="tooltip" v-if="isSidebarCollapsed">{{ item.title }}</div>
        </div>
      </nav>

      <div class="sidebar-footer">
        <div class="user-profile" @click="toggleUserMenu">
          <div class="avatar">
            <span class="material-icons">account_circle</span>
          </div>
          <div class="user-info" v-show="!isSidebarCollapsed">
            <h3>商家用户</h3>
            <p>高级卖家</p>
          </div>
          <div class="dropdown-menu" v-if="isUserMenuVisible">
            <div class="dropdown-item">
              <span class="material-icons">person</span>
              <span>个人资料</span>
            </div>
            <div class="dropdown-item">
              <span class="material-icons">settings</span>
              <span>账户设置</span>
            </div>
            <div class="dropdown-item" @click="logout">
              <span class="material-icons">logout</span>
              <span>退出登录</span>
            </div>
          </div>
        </div>
      </div>
    </aside>

    <!-- 主内容区 -->
    <main class="main-content">
      <!-- 顶部导航条 -->
      <header class="top-header">
        <div class="header-left">
          <div class="page-title">
            <h2>{{ currentPageTitle }}</h2>
          </div>
        </div>
        <div class="header-right">
          <div class="deepseek-banner">
            <span class="material-icons ai-icon">psychology</span>
            已全面接入满血DeepSeek
          </div>
          <div class="search-box">
            <span class="material-icons">search</span>
            <input type="text" placeholder="搜索..." />
          </div>
          <div class="notification" @click="toggleNotifications">
            <span class="material-icons">notifications</span>
            <span class="badge">3</span>
            <div class="dropdown-menu" v-if="isNotificationsVisible">
              <div class="dropdown-header">
                <h3>通知</h3>
                <span>标记全部已读</span>
              </div>
              <div class="notification-item unread">
                <div class="notification-icon order">
                  <span class="material-icons">shopping_bag</span>
                </div>
                <div class="notification-content">
                  <p>您有一个新订单需要处理</p>
                  <span class="time">5分钟前</span>
                </div>
              </div>
              <div class="notification-item unread">
                <div class="notification-icon message">
                  <span class="material-icons">message</span>
                </div>
                <div class="notification-content">
                  <p>来自客户张先生的新消息</p>
                  <span class="time">30分钟前</span>
                </div>
              </div>
              <div class="notification-item">
                <div class="notification-icon system">
                  <span class="material-icons">info</span>
                </div>
                <div class="notification-content">
                  <p>系统将于今晚23:00-24:00进行维护</p>
                  <span class="time">2小时前</span>
                </div>
              </div>
              <div class="dropdown-footer">
                <span>查看全部通知</span>
              </div>
            </div>
          </div>
          <div class="theme-toggle" @click="toggleTheme">
            <span class="material-icons">{{ isDarkMode ? 'light_mode' : 'dark_mode' }}</span>
          </div>
        </div>
      </header>

      <!-- AI助手内容区域 -->
      <div class="content-wrapper">
        <div class="ai-assistant-container">
          <div class="chat-container">
            <div class="chat-header">
              <div class="chat-title">
                <span class="material-icons">smart_toy</span>
                <h2>AI智能助手</h2>
              </div>
              <div class="chat-actions">
                <button class="clear-btn" @click="clearChat">
                  <span class="material-icons">delete_sweep</span>
                  清空对话
                </button>
              </div>
            </div>
            
            <div class="chat-messages" ref="messagesContainer">
              <div v-for="(message, index) in messages" :key="index" 
                   :class="['message', message.sender === 'ai' ? 'ai-message' : 'user-message']">
                <div class="message-avatar">
                  <span class="material-icons">{{ message.sender === 'ai' ? 'smart_toy' : 'person' }}</span>
                </div>
                <div class="message-content">
                  <div class="message-bubble">
                    <p v-html="formatMessage(message.text)"></p>
                  </div>
                  <div class="message-time">{{ message.time }}</div>
                </div>
              </div>
              <div v-if="isLoading" class="message ai-message">
                <div class="message-avatar">
                  <span class="material-icons">smart_toy</span>
                </div>
                <div class="message-content">
                  <div class="message-bubble typing-indicator">
                    <span></span>
                    <span></span>
                    <span></span>
                  </div>
                </div>
              </div>
            </div>
            
            <div class="chat-input-container">
              <textarea 
                class="chat-input" 
                v-model="userInput" 
                placeholder="输入问题..." 
                @keydown.enter.prevent="sendMessage"
                ref="chatInput"
              ></textarea>
              <button 
                class="send-button" 
                @click="sendMessage" 
                :disabled="isLoading || !userInput.trim()"
              >
                <span class="material-icons">send</span>
              </button>
            </div>
          </div>
          
          <div class="ai-sidebar">
            <div class="ai-sidebar-section">
              <h3>常见问题</h3>
              <div class="question-chips">
                <div class="question-chip" v-for="(question, idx) in commonQuestions" :key="idx" @click="useQuestion(question)">
                  {{ question }}
                </div>
              </div>
            </div>
            
            <div class="ai-sidebar-section">
              <h3>使用指南</h3>
              <div class="guide-card">
                <p><strong>您可以询问以下问题：</strong></p>
                <ul>
                  <li>商品管理相关问题</li>
                  <li>订单处理方法</li>
                  <li>退款政策</li>
                  <li>库存管理策略</li>
                  <li>平台规则与政策</li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
import axios from 'axios';
import { computed, nextTick, onBeforeUnmount, onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';

export default {
  name: 'AIAssistant',
  setup() {
    const router = useRouter();
    
    // 主题模式控制
    const isDarkMode = ref(localStorage.getItem('theme') === 'dark' || false);
    
    // 侧边栏状态控制
    const isSidebarCollapsed = ref(localStorage.getItem('sidebarCollapsed') === 'true' || false);
    const activeNavIndex = ref(9); // AI咨询的索引
    
    // 下拉菜单状态控制
    const isUserMenuVisible = ref(false);
    const isNotificationsVisible = ref(false);
    
    // AI聊天API相关状态
    const userId = ref(localStorage.getItem('userId') || 'user' + Math.floor(Math.random() * 10000)); // 模拟用户ID
    const sessionId = ref(localStorage.getItem('chatSessionId') || ''); // 从本地存储加载会话ID
    
    // 导航菜单项
    const navItems = ref([
      { title: '控制面板', icon: 'dashboard', path: '/dashboard' },
      { title: '店铺管理', icon: 'storefront', path: '/store' },
      { title: '商品管理', icon: 'inventory_2', path: '/products' },
      { title: '订单管理', icon: 'shopping_cart', path: '/orders' },
      { title: '客户管理', icon: 'people', path: '/customers' },
      { title: '营销中心', icon: 'campaign', path: '/marketing' },
      { title: '消息中心', icon: 'message', path: '/messages' },
      { title: '财务管理', icon: 'payments', path: '/finance' },
      { title: '数据分析', icon: 'analytics', path: '/analytics' },
      { title: 'AI咨询', icon: 'smart_toy', path: '/ai-assistant' },
      { title: '设置中心', icon: 'settings', path: '/settings' }
    ]);
    
    // AI助手相关状态
    const userInput = ref('');
    const messages = ref([
      {
        sender: 'ai',
        text: '您好！我是您的电商AI助手。有任何关于店铺运营、商品管理或订单处理的问题，请随时询问我。',
        time: getCurrentTime()
      }
    ]);
    const isLoading = ref(false);
    const messagesContainer = ref(null);
    const chatInput = ref(null);
    const commonQuestions = ref([
      '如何提高商品曝光率？',
      '处理退款的最佳方式是什么？',
      '如何优化商品描述？',
      '库存预警设置怎么调整？',
      '如何处理差评？'
    ]);

    // 当前页面标题
    const currentPageTitle = computed(() => {
      return navItems.value[activeNavIndex.value].title;
    });
    
    // 发送消息
    const sendMessage = async () => {
      if (!userInput.value.trim() || isLoading.value) return;
      
      // 添加用户消息到聊天界面
      messages.value.push({
        sender: 'user',
        text: userInput.value,
        time: getCurrentTime()
      });
      
      const userQuestion = userInput.value;
      userInput.value = '';
      scrollToBottom();
      
      // 发送请求到后端API
      isLoading.value = true;
      try {
        // 使用完整的请求URL并添加更多的请求配置
        console.log('发送请求数据:', { message: userQuestion, userId: userId.value, sessionId: sessionId.value || undefined });
        
        const response = await axios({
          method: 'post',
          url: '/admin/chat/send',
          data: {
            message: userQuestion,
            userId: userId.value,
            sessionId: sessionId.value || undefined
          },
          headers: {
            'Content-Type': 'application/json'
          },
          timeout: 600000 // 30秒超时
        });
        
        // 调试输出
        console.log('接收到的响应:', response.data);
        
        // 处理API响应
        if (response.data) {
          // 保存会话ID
          if (response.data.sessionId) {
            sessionId.value = response.data.sessionId;
            // 保存到localStorage以便跨会话使用
            localStorage.setItem('chatSessionId', response.data.sessionId);
          }
          
          // 显示AI回复
          messages.value.push({
            sender: 'ai',
            text: response.data.response || '抱歉，AI没有返回有效回复',
            time: getCurrentTime()
          });
        } else {
          throw new Error('返回数据格式不正确');
        }
      } catch (error) {
        console.error('AI聊天请求失败:', error);
        
        // 更详细的错误处理
        let errorMessage = '抱歉，我暂时无法回答您的问题。请稍后再试。';
        
        if (error.response) {
          // 服务器返回了错误状态码
          console.error('错误响应数据:', error.response.data);
          console.error('错误状态码:', error.response.status);
          
          if (error.response.status === 404) {
            errorMessage = '找不到聊天服务接口，请确认后端服务已启动。';
          } else if (error.response.status === 500) {
            errorMessage = '服务器内部错误，请联系管理员。';
          }
        } else if (error.request) {
          // 请求已发送但没有收到响应
          console.error('没有收到响应:', error.request);
          errorMessage = '无法连接到服务器，请检查您的网络连接或稍后再试。';
        } else {
          // 请求配置有问题
          console.error('请求错误:', error.message);
        }
        
        // 显示错误消息
        messages.value.push({
          sender: 'ai',
          text: errorMessage,
          time: getCurrentTime()
        });
      } finally {
        isLoading.value = false;
        scrollToBottom();
      }
    };
    
    // 使用预设问题
    const useQuestion = (question) => {
      userInput.value = question;
      if (chatInput.value) {
        chatInput.value.focus();
      }
    };
    
    // 清空对话
    const clearChat = () => {
      messages.value = [{
        sender: 'ai',
        text: '对话已清空。有什么我可以帮您的吗？',
        time: getCurrentTime()
      }];
      // 清空会话ID，开始新对话
      sessionId.value = '';
    };
    
    // 滚动到底部
    const scrollToBottom = () => {
      nextTick(() => {
        if (messagesContainer.value) {
          messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight;
        }
      });
    };
    
    // 格式化消息文本
    const formatMessage = (text) => {
      // 处理markdown格式的文本（粗体、链接、列表等基本格式）
      let formattedText = text
        // 粗体 **text**
        .replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>')
        // 斜体 *text*
        .replace(/\*(.*?)\*/g, '<em>$1</em>')
        // 链接检测和格式化
        .replace(/(https?:\/\/[^\s]+)/g, '<a href="$1" target="_blank">$1</a>')
        // 列表项
        .replace(/^\s*-\s+(.*?)$/gm, '<li>$1</li>')
        // 换行
        .replace(/\n/g, '<br>');
        
      // 处理列表（将连续的<li>包装在<ul>中）
      formattedText = formattedText.replace(/<li>.*?<\/li>(\s*<li>.*?<\/li>)+/g, function(match) {
        return '<ul>' + match + '</ul>';
      });
        
      return formattedText;
    };
    
    // 获取当前时间
    function getCurrentTime() {
      const now = new Date();
      const hours = now.getHours().toString().padStart(2, '0');
      const minutes = now.getMinutes().toString().padStart(2, '0');
      return `${hours}:${minutes}`;
    }

    // 主题切换
    const toggleTheme = () => {
      isDarkMode.value = !isDarkMode.value;
      localStorage.setItem('theme', isDarkMode.value ? 'dark' : 'light');
      document.documentElement.classList.toggle('dark-theme', isDarkMode.value);
    };
    
    // 侧边栏切换
    const toggleSidebar = () => {
      isSidebarCollapsed.value = !isSidebarCollapsed.value;
      localStorage.setItem('sidebarCollapsed', isSidebarCollapsed.value.toString());
    };
    
    // 设置活动导航项
    const setActiveNav = (index) => {
      activeNavIndex.value = index;
      
      // 使用直接路径进行跳转
      switch(index) {
        case 0:
          router.push('/dashboard');
          break;
        case 1:
          router.push('/store');
          break;
        case 2:
          router.push('/products');
          break;
        case 3:
          router.push('/orders');
          break;
        case 4:
          router.push('/customers');
          break;
        case 5:
          router.push('/marketing');
          break;
        case 6:
          router.push('/messages');
          break;
        case 7:
          router.push('/finance');
          break;
        case 8:
          router.push('/analytics');
          break;
        case 9:
          router.push('/ai-assistant');
          break;
        case 10:
          router.push('/settings');
          break;
        default:
          router.push('/dashboard');
      }
      
      // 在移动端视图中点击导航项后收起侧边栏
      if (window.innerWidth <= 768) {
        isSidebarCollapsed.value = true;
      }
      
      // 关闭可能打开的下拉菜单
      isUserMenuVisible.value = false;
      isNotificationsVisible.value = false;
    };
    
    // 切换用户菜单
    const toggleUserMenu = () => {
      isUserMenuVisible.value = !isUserMenuVisible.value;
      if (isUserMenuVisible.value) {
        isNotificationsVisible.value = false;
      }
    };
    
    // 切换通知菜单
    const toggleNotifications = () => {
      isNotificationsVisible.value = !isNotificationsVisible.value;
      if (isNotificationsVisible.value) {
        isUserMenuVisible.value = false;
      }
    };
    
    // 登出功能
    const logout = () => {
      // 实际项目中实现登出逻辑，如清除会话、令牌等
      router.push('/login');
    };
    
    // 背景动画效果
    const handleMouseMove = (e) => {
      const shapes = document.querySelectorAll('.shape');
      const x = e.clientX / window.innerWidth;
      const y = e.clientY / window.innerHeight;
      
      shapes.forEach((shape, index) => {
        const speed = 1 - (index * 0.2);
        const offsetX = 30 * (x - 0.5) * speed;
        const offsetY = 30 * (y - 0.5) * speed;
        shape.style.transform = `translate(${offsetX}px, ${offsetY}px)`;
      });
    };
    
    // 点击文档其他区域关闭下拉菜单
    const handleClickOutside = (e) => {
      if (isUserMenuVisible.value && !e.target.closest('.user-profile')) {
        isUserMenuVisible.value = false;
      }
      if (isNotificationsVisible.value && !e.target.closest('.notification')) {
        isNotificationsVisible.value = false;
      }
    };
    
    // 窗口大小变化处理
    const handleResize = () => {
      if (window.innerWidth <= 768 && !isSidebarCollapsed.value) {
        isSidebarCollapsed.value = true;
      }
    };
    
    onMounted(() => {
      // 初始化主题
      document.documentElement.classList.toggle('dark-theme', isDarkMode.value);
      
      // 添加事件监听器
      document.addEventListener('mousemove', handleMouseMove);
      document.addEventListener('click', handleClickOutside);
      window.addEventListener('resize', handleResize);
      
      // 初始检查窗口大小
      handleResize();
      
      // 聚焦输入框
      nextTick(() => {
        if (chatInput.value) {
          chatInput.value.focus();
        }
        scrollToBottom();
      });
    });
    
    onBeforeUnmount(() => {
      // 移除事件监听器
      document.removeEventListener('mousemove', handleMouseMove);
      document.removeEventListener('click', handleClickOutside);
      window.removeEventListener('resize', handleResize);
    });
    
    return {
      isDarkMode,
      isSidebarCollapsed,
      activeNavIndex,
      isUserMenuVisible,
      isNotificationsVisible,
      navItems,
      currentPageTitle,
      userInput,
      messages,
      isLoading,
      messagesContainer,
      chatInput,
      commonQuestions,
      userId,
      sessionId,
      toggleTheme,
      toggleSidebar,
      setActiveNav,
      toggleUserMenu,
      toggleNotifications,
      logout,
      sendMessage,
      clearChat,
      useQuestion,
      formatMessage
    };
  }
};
</script>

<style scoped>
  /* Base Styles */
  @import url('https://fonts.googleapis.com/css2?family=Material+Icons');
  
  * {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
  }
  
  .home-container {
    display: flex;
    width: 100%;
    min-height: 100vh;
    background: linear-gradient(135deg, #f5f7fa 0%, #e4e9f0 100%);
    color: #333;
    font-family: 'Roboto', 'Helvetica Neue', Arial, sans-serif;
    position: relative;
    overflow: hidden;
    transition: all 0.3s ease;
  }
  
  /* Dark Mode Styles */
  .home-container.dark-mode {
    background: linear-gradient(135deg, #1f2229 0%, #121317 100%);
    color: #f0f0f0;
  }
  
  /* Background Animation Effects */
  .background-shapes {
    position: absolute;
    width: 100%;
    height: 100%;
    overflow: hidden;
    z-index: 0;
  }
  
  .shape {
    position: absolute;
    border-radius: 50%;
    background: radial-gradient(circle, rgba(212, 175, 55, 0.1) 0%, rgba(212, 175, 55, 0.05) 70%);
    transition: transform 0.3s ease-out;
  }
  
  .shape-1 {
    width: 600px;
    height: 600px;
    top: -200px;
    right: -100px;
    animation: float 15s infinite alternate;
  }
  
  .shape-2 {
    width: 400px;
    height: 400px;
    bottom: -150px;
    left: -100px;
    animation: float 20s infinite alternate-reverse;
  }
  
  .shape-3 {
    width: 300px;
    height: 300px;
    top: 40%;
    right: 25%;
    animation: float 25s infinite alternate;
  }
  
  .dark-mode .shape {
    background: radial-gradient(circle, rgba(212, 175, 55, 0.08) 0%, rgba(212, 175, 55, 0.03) 70%);
  }
  
  @keyframes float {
    0% {
      transform: translateY(0) translateX(0);
    }
    50% {
      transform: translateY(-20px) translateX(10px);
    }
    100% {
      transform: translateY(20px) translateX(-10px);
    }
  }
  
  /* Sidebar Styles */
  .sidebar {
    width: 280px;
    background: rgba(255, 255, 255, 0.9);
    backdrop-filter: blur(10px);
    height: 100vh;
    display: flex;
    flex-direction: column;
    position: sticky;
    top: 0;
    left: 0;
    z-index: 100;
    box-shadow: 0 0 25px rgba(0, 0, 0, 0.05);
    transition: all 0.3s ease;
    overflow-y: auto;
    border-right: 1px solid rgba(0, 0, 0, 0.08);
  }
  
  .dark-mode .sidebar {
    background: rgba(20, 21, 26, 0.9);
    border-right: 1px solid rgba(212, 175, 55, 0.1);
    box-shadow: 0 0 25px rgba(0, 0, 0, 0.2);
  }
  
  .sidebar.collapsed {
    width: 80px;
  }
  
  .sidebar-header {
    padding: 30px 20px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    border-bottom: 1px solid rgba(0, 0, 0, 0.08);
  }
  
  .dark-mode .sidebar-header {
    border-bottom: 1px solid rgba(255, 255, 255, 0.05);
  }
  
  .brand-container {
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 100%;
  }
  
  .brand-name {
    font-size: 1.8rem;
    font-weight: 700;
    background: linear-gradient(to right, #d4af37, #f8df81, #d4af37);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
    text-fill-color: transparent;
    margin: 0;
    white-space: nowrap;
    overflow: hidden;
    transition: all 0.3s ease;
  }
  
  .sidebar.collapsed .brand-name {
    font-size: 0;
    width: 0;
    opacity: 0;
  }
  
  .sidebar-toggle {
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #555;
    transition: all 0.3s ease;
  }
  
  .dark-mode .sidebar-toggle {
    color: #d4af37;
  }
  
  .sidebar-toggle:hover {
    color: #d4af37;
    transform: scale(1.1);
  }
  
  .dark-mode .sidebar-toggle:hover {
    color: #f8df81;
  }
  
  /* Sidebar Navigation */
  .sidebar-nav {
    flex: 1;
    padding: 20px 0;
    overflow-y: auto;
  }
  
  .nav-item {
    display: flex;
    align-items: center;
    padding: 12px 24px;
    color: #555;
    cursor: pointer;
    position: relative;
    transition: all 0.3s ease;
    margin: 4px 8px;
    border-radius: 10px;
  }
  
  .dark-mode .nav-item {
    color: #aaa;
  }
  
  .nav-item:hover {
    background: rgba(0, 0, 0, 0.04);
    color: #d4af37;
  }
  
  .dark-mode .nav-item:hover {
    background: rgba(255, 255, 255, 0.04);
    color: #d4af37;
  }
  
  .nav-item.active {
    background: linear-gradient(90deg, rgba(212, 175, 55, 0.15) 0%, rgba(212, 175, 55, 0.05) 100%);
    color: #d4af37;
    box-shadow: 0 4px 15px rgba(212, 175, 55, 0.1);
  }
  
  .dark-mode .nav-item.active {
    background: linear-gradient(90deg, rgba(212, 175, 55, 0.2) 0%, rgba(212, 175, 55, 0.05) 100%);
    color: #f8df81;
  }
  
  .nav-item .material-icons {
    margin-right: 16px;
    font-size: 24px;
    transition: all 0.3s ease;
  }
  
  .sidebar.collapsed .nav-item {
    padding: 12px 0;
    justify-content: center;
  }
  
  .sidebar.collapsed .nav-item .material-icons {
    margin-right: 0;
  }
  
  .sidebar.collapsed .nav-title {
    display: none;
  }
  
  .tooltip {
    position: absolute;
    left: 100%;
    top: 50%;
    transform: translateY(-50%);
    background: rgba(0, 0, 0, 0.8);
    color: white;
    padding: 6px 12px;
    border-radius: 6px;
    font-size: 14px;
    white-space: nowrap;
    opacity: 0;
    visibility: hidden;
    transition: all 0.2s ease;
    z-index: 1000;
    margin-left: 10px;
  }
  
  .tooltip::before {
    content: '';
    position: absolute;
    top: 50%;
    left: -5px;
    transform: translateY(-50%);
    border-style: solid;
    border-width: 5px 5px 5px 0;
    border-color: transparent rgba(0, 0, 0, 0.8) transparent transparent;
  }
  
  .nav-item:hover .tooltip {
    opacity: 1;
    visibility: visible;
  }

/* 用户菜单 */
.sidebar-footer {
  padding: 20px;
  border-top: 1px solid rgba(0, 0, 0, 0.08);
}

.dark-mode .sidebar-footer {
  border-top: 1px solid rgba(255, 255, 255, 0.05);
}

.user-profile {
  display: flex;
  align-items: center;
  padding: 10px;
  border-radius: 12px;
  cursor: pointer;
  position: relative;
  transition: all 0.3s ease;
}

.user-profile:hover {
  background: rgba(0, 0, 0, 0.04);
}

.dark-mode .user-profile:hover {
  background: rgba(255, 255, 255, 0.04);
}

.avatar {
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  background: linear-gradient(135deg, #d4af37, #f8df81);
  color: white;
  margin-right: 12px;
}

.sidebar.collapsed .avatar {
  margin-right: 0;
}

.user-info {
  overflow: hidden;
}

.user-info h3 {
  font-size: 16px;
  font-weight: 500;
  margin-bottom: 2px;
  white-space: nowrap;
}

.user-info p {
  font-size: 12px;
  opacity: 0.7;
  white-space: nowrap;
}

.dropdown-menu {
  position: absolute;
  top: calc(100% + 10px);
  left: 0;
  width: 200px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 5px 30px rgba(0, 0, 0, 0.15);
  padding: 10px;
  z-index: 1000;
  transition: all 0.3s ease;
}

.dark-mode .dropdown-menu {
  background: #1f2229;
  box-shadow: 0 5px 30px rgba(0, 0, 0, 0.3);
  border: 1px solid rgba(255, 255, 255, 0.05);
}

.dropdown-item {
  display: flex;
  align-items: center;
  padding: 10px;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.2s ease;
}

.dropdown-item:hover {
  background: rgba(0, 0, 0, 0.04);
  color: #d4af37;
}

.dark-mode .dropdown-item:hover {
  background: rgba(255, 255, 255, 0.04);
  color: #f8df81;
}

.dropdown-item .material-icons {
  margin-right: 10px;
  font-size: 20px;
}

/* 主内容区域 */
.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  position: relative;
  z-index: 1;
  padding: 0 20px;
}

/* 顶部导航条 */
.top-header {
  height: 80px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  position: sticky;
  top: 0;
  background: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(10px);
  z-index: 99;
  border-bottom: 1px solid rgba(0, 0, 0, 0.05);
}

.dark-mode .top-header {
  background: rgba(20, 21, 26, 0.8);
  border-bottom: 1px solid rgba(255, 255, 255, 0.05);
}

.page-title h2 {
  font-size: 1.8rem;
  font-weight: 600;
  color: #333;
}

.dark-mode .page-title h2 {
  color: #f0f0f0;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 20px;
}

.deepseek-banner {
  display: flex;
  align-items: center;
  background: linear-gradient(135deg, rgba(212, 175, 55, 0.1), rgba(212, 175, 55, 0.2));
  border-radius: 8px;
  padding: 6px 12px;
  margin-right: 15px;
  font-size: 14px;
  font-weight: 500;
  color: #d4af37;
  animation: pulse 2s infinite ease-in-out;
  box-shadow: 0 0 10px rgba(212, 175, 55, 0.2);
  border: 1px solid rgba(212, 175, 55, 0.2);
}

.dark-mode .deepseek-banner {
  background: linear-gradient(135deg, rgba(212, 175, 55, 0.15), rgba(212, 175, 55, 0.25));
  color: #f8df81;
  box-shadow: 0 0 10px rgba(212, 175, 55, 0.3);
  border: 1px solid rgba(212, 175, 55, 0.3);
}

.deepseek-banner .ai-icon {
  margin-right: 8px;
  font-size: 20px;
  animation: spin 8s infinite linear;
}

@keyframes pulse {
  0% {
    transform: scale(1);
    opacity: 1;
  }
  50% {
    transform: scale(1.03);
    opacity: 0.9;
  }
  100% {
    transform: scale(1);
    opacity: 1;
  }
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  25% {
    transform: rotate(10deg);
  }
  75% {
    transform: rotate(-10deg);
  }
  100% {
    transform: rotate(0deg);
  }
}

.search-box {
  display: flex;
  align-items: center;
  background: rgba(0, 0, 0, 0.04);
  border-radius: 20px;
  padding: 8px 16px;
  width: 260px;
  transition: all 0.3s ease;
}

.dark-mode .search-box {
  background: rgba(255, 255, 255, 0.05);
}

.search-box:focus-within {
  background: rgba(0, 0, 0, 0.06);
  box-shadow: 0 0 0 2px rgba(212, 175, 55, 0.3);
}

.dark-mode .search-box:focus-within {
  background: rgba(255, 255, 255, 0.08);
}

.search-box .material-icons {
  color: #777;
  margin-right: 8px;
}

.dark-mode .search-box .material-icons {
  color: #aaa;
}

.search-box input {
  border: none;
  background: transparent;
  outline: none;
  font-size: 14px;
  color: #333;
  width: 100%;
}

.dark-mode .search-box input {
  color: #f0f0f0;
}

.search-box input::placeholder {
  color: #888;
}

.dark-mode .search-box input::placeholder {
  color: #aaa;
}

.notification, .theme-toggle {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  color: #555;
}

.dark-mode .notification, .dark-mode .theme-toggle {
  color: #bbb;
}

.notification:hover, .theme-toggle:hover {
  background: rgba(0, 0, 0, 0.05);
  color: #d4af37;
  transform: translateY(-2px);
}

.dark-mode .notification:hover, .dark-mode .theme-toggle:hover {
  background: rgba(255, 255, 255, 0.05);
  color: #f8df81;
}

.badge {
  position: absolute;
  top: 0;
  right: 0;
  width: 20px;
  height: 20px;
  background: #d4af37;
  color: white;
  font-size: 12px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 600;
}

.notification .dropdown-menu {
  width: 320px;
  right: 0;
  left: auto;
  padding: 0;
  overflow: hidden;
}

.dropdown-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 16px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.08);
}

.dark-mode .dropdown-header {
  border-bottom: 1px solid rgba(255, 255, 255, 0.05);
}

.dropdown-header h3 {
  font-size: 16px;
  font-weight: 600;
}

.dropdown-header span {
  font-size: 13px;
  color: #d4af37;
  cursor: pointer;
}

.dark-mode .dropdown-header span {
  color: #f8df81;
}

.notification-item {
  display: flex;
  padding: 16px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.04);
  transition: all 0.3s ease;
}

.dark-mode .notification-item {
  border-bottom: 1px solid rgba(255, 255, 255, 0.03);
}

.notification-item:last-child {
  border-bottom: none;
}

.notification-item:hover {
  background: rgba(0, 0, 0, 0.01);
}

.dark-mode .notification-item:hover {
  background: rgba(255, 255, 255, 0.01);
}

.notification-item.unread {
  background: rgba(212, 175, 55, 0.05);
}

.dark-mode .notification-item.unread {
  background: rgba(212, 175, 55, 0.08);
}

.notification-icon {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 16px;
}

.notification-icon.order {
  background: rgba(253, 126, 20, 0.1);
  color: #fd7e14;
}

.notification-icon.message {
  background: rgba(13, 110, 253, 0.1);
  color: #0d6efd;
}

.notification-icon.system {
  background: rgba(108, 117, 125, 0.1);
  color: #6c757d;
}

.notification-content {
  flex: 1;
}

.notification-content p {
  margin-bottom: 4px;
  font-size: 14px;
}

.notification-content .time {
  font-size: 12px;
  color: #888;
}

.dark-mode .notification-content .time {
  color: #aaa;
}

.dropdown-footer {
  padding: 12px;
  text-align: center;
  border-top: 1px solid rgba(0, 0, 0, 0.08);
  font-size: 14px;
  color: #d4af37;
  cursor: pointer;
}

.dark-mode .dropdown-footer {
  border-top: 1px solid rgba(255, 255, 255, 0.05);
  color: #f8df81;
}

/* 内容区域 */
.content-wrapper {
  flex: 1;
  padding: 30px 0;
}

/* AI助手容器样式 */
.ai-assistant-container {
  display: flex;
  height: 100%;
  gap: 25px;
  max-width: 1400px;
  margin: 0 auto;
  padding-bottom: 20px;
}

.chat-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  background: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(10px);
  border-radius: 20px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.05);
  overflow: hidden;
  border: 1px solid rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
  height: 100%;
}

.dark-mode .chat-container {
  background: rgba(31, 34, 41, 0.9);
  border: 1px solid rgba(255, 255, 255, 0.05);
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
}

.chat-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.05);
}

.dark-mode .chat-header {
  border-bottom: 1px solid rgba(255, 255, 255, 0.05);
}

.chat-title {
  display: flex;
  align-items: center;
  gap: 12px;
}

.chat-title .material-icons {
  color: #d4af37;
  font-size: 28px;
}

.chat-title h2 {
  font-size: 1.5rem;
  font-weight: 600;
  margin: 0;
}

.chat-actions {
  display: flex;
  gap: 10px;
}

.clear-btn {
  display: flex;
  align-items: center;
  gap: 5px;
  background: rgba(0, 0, 0, 0.05);
  border: none;
  border-radius: 8px;
  padding: 8px 12px;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.2s ease;
  color: #666;
}

.dark-mode .clear-btn {
  background: rgba(255, 255, 255, 0.05);
  color: #aaa;
}

.clear-btn:hover {
  background: rgba(0, 0, 0, 0.1);
  color: #d4af37;
}

.dark-mode .clear-btn:hover {
  background: rgba(255, 255, 255, 0.1);
  color: #f8df81;
}

.chat-messages {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.message {
  display: flex;
  gap: 12px;
  max-width: 80%;
}

.user-message {
  align-self: flex-end;
  flex-direction: row-reverse;
}

.message-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.user-message .message-avatar {
  background: linear-gradient(135deg, #6e8efb, #a777e3);
  color: white;
}

.ai-message .message-avatar {
  background: linear-gradient(135deg, #d4af37, #f8df81);
  color: white;
}

.message-content {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.message-bubble {
  padding: 12px 16px;
  border-radius: 18px;
  position: relative;
  word-break: break-word;
}

.user-message .message-bubble {
  background: linear-gradient(135deg, #6e8efb, #a777e3);
  color: white;
  border-top-right-radius: 4px;
}

.ai-message .message-bubble {
  background: rgba(0, 0, 0, 0.04);
  color: #333;
  border-top-left-radius: 4px;
}

.dark-mode .ai-message .message-bubble {
  background: rgba(255, 255, 255, 0.08);
  color: #f0f0f0;
}

.message-bubble p {
  margin: 0;
  line-height: 1.5;
}

/* 添加Markdown相关样式 */
.message-bubble strong {
  font-weight: bold;
}

.message-bubble em {
  font-style: italic;
}

.message-bubble ul {
  margin: 8px 0;
  padding-left: 20px;
}

.message-bubble li {
  margin-bottom: 4px;
}

.message-bubble a {
  color: #d4af37;
  text-decoration: none;
}

.dark-mode .message-bubble a {
  color: #f8df81;
}

.message-bubble a:hover {
  text-decoration: underline;
}

.message-time {
  font-size: 12px;
  color: #888;
  align-self: flex-end;
}

.dark-mode .message-time {
  color: #aaa;
}

.user-message .message-time {
  align-self: flex-start;
}

.typing-indicator {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 5px;
  padding: 12px 16px;
  min-width: 60px;
}

.typing-indicator span {
  display: inline-block;
  width: 8px;
  height: 8px;
  background-color: rgba(0, 0, 0, 0.3);
  border-radius: 50%;
  animation: typing 1s infinite ease-in-out;
}

.dark-mode .typing-indicator span {
  background-color: rgba(255, 255, 255, 0.3);
}

.typing-indicator span:nth-child(1) {
  animation-delay: 0s;
}

.typing-indicator span:nth-child(2) {
  animation-delay: 0.2s;
}

.typing-indicator span:nth-child(3) {
  animation-delay: 0.4s;
}

@keyframes typing {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-5px);
  }
}

.chat-input-container {
  padding: 20px;
  border-top: 1px solid rgba(0, 0, 0, 0.05);
  display: flex;
  gap: 10px;
}

.dark-mode .chat-input-container {
  border-top: 1px solid rgba(255, 255, 255, 0.05);
}

.chat-input {
  flex: 1;
  border: none;
  outline: none;
  background: rgba(0, 0, 0, 0.04);
  border-radius: 20px;
  padding: 12px 20px;
  font-size: 14px;
  resize: none;
  height: 50px;
  max-height: 150px;
  transition: all 0.3s ease;
  font-family: inherit;
}

.dark-mode .chat-input {
  background: rgba(255, 255, 255, 0.05);
  color: #f0f0f0;
}

.chat-input:focus {
  background: rgba(0, 0, 0, 0.06);
  box-shadow: 0 0 0 2px rgba(212, 175, 55, 0.3);
}

.dark-mode .chat-input:focus {
  background: rgba(255, 255, 255, 0.08);
}

.send-button {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  border: none;
  background: linear-gradient(135deg, #d4af37, #f8df81);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.3s ease;
  flex-shrink: 0;
}

.send-button:hover {
  transform: scale(1.05);
  box-shadow: 0 5px 15px rgba(212, 175, 55, 0.3);
}

.send-button:disabled {
  background: #ccc;
  cursor: not-allowed;
  transform: none;
  box-shadow: none;
}

.dark-mode .send-button:disabled {
  background: #444;
}

/* AI sidebar styles */
.ai-sidebar {
  width: 300px;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.ai-sidebar-section {
  background: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(10px);
  border-radius: 20px;
  padding: 20px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.05);
  border: 1px solid rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
}

.dark-mode .ai-sidebar-section {
  background: rgba(31, 34, 41, 0.9);
  border: 1px solid rgba(255, 255, 255, 0.05);
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
}

.ai-sidebar-section h3 {
  font-size: 16px;
  font-weight: 600;
  margin-top: 0;
  margin-bottom: 15px;
  color: #333;
}

.dark-mode .ai-sidebar-section h3 {
  color: #f0f0f0;
}

.question-chips {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.question-chip {
  background: rgba(0, 0, 0, 0.04);
  border-radius: 8px;
  padding: 10px 15px;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.2s ease;
}

.dark-mode .question-chip {
  background: rgba(255, 255, 255, 0.05);
}

.question-chip:hover {
  background: rgba(212, 175, 55, 0.1);
  color: #d4af37;
  transform: translateX(5px);
}

.dark-mode .question-chip:hover {
  background: rgba(212, 175, 55, 0.15);
  color: #f8df81;
}

.guide-card {
  background: rgba(0, 0, 0, 0.02);
  border-radius: 10px;
  padding: 15px;
}

.dark-mode .guide-card {
  background: rgba(255, 255, 255, 0.02);
}

.guide-card p {
  margin-top: 0;
  margin-bottom: 10px;
}

.guide-card ul {
  margin: 0;
  padding-left: 20px;
}

.guide-card li {
  margin-bottom: 5px;
  font-size: 14px;
}

/* 响应式设计 */
@media (max-width: 992px) {
  .ai-assistant-container {
    flex-direction: column;
  }
  
  .ai-sidebar {
    width: 100%;
    flex-direction: row;
    flex-wrap: wrap;
  }
  
  .ai-sidebar-section {
    flex: 1;
    min-width: 300px;
  }
}

@media (max-width: 768px) {
  .message {
    max-width: 90%;
  }
  
  .chat-header {
    padding: 15px;
  }
  
  .chat-title h2 {
    font-size: 1.2rem;
  }
  
  .deepseek-banner {
    font-size: 12px;
    padding: 4px 8px;
  }
}

@media (max-width: 576px) {
  .ai-assistant-container {
    padding: 0;
  }
  
  .message {
    max-width: 95%;
  }
  
  .clear-btn span:not(.material-icons) {
    display: none;
  }
  
  .deepseek-banner span:not(.material-icons) {
    max-width: 80px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
}
</style> 