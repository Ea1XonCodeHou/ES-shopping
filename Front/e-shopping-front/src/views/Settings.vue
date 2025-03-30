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

      <!-- 设置内容区域 -->
      <div class="content-wrapper">
        <div class="settings-container">
          <!-- 设置导航菜单 -->
          <div class="settings-sidebar">
            <div 
              v-for="(section, index) in settingsSections" 
              :key="index"
              :class="['settings-nav-item', { active: activeSettingsSection === index }]"
              @click="activeSettingsSection = index"
            >
              <span class="material-icons">{{ section.icon }}</span>
              <span class="settings-nav-title">{{ section.title }}</span>
            </div>
          </div>
          
          <!-- 设置内容区域 -->
          <div class="settings-content">
            <!-- 个人信息设置 -->
            <div v-if="activeSettingsSection === 0" class="settings-section">
              <h2 class="section-title">个人信息</h2>
              <div class="settings-card">
                <div class="profile-header">
                  <div class="profile-avatar">
                    <img src="https://via.placeholder.com/100" alt="用户头像">
                    <div class="avatar-upload">
                      <span class="material-icons">file_upload</span>
                    </div>
                  </div>
                  <div class="profile-info">
                    <h3>商家用户</h3>
                    <p>账号创建于 2023年10月15日</p>
                  </div>
                </div>
                
                <div class="form-group">
                  <label>用户名</label>
                  <input type="text" v-model="userProfile.username" placeholder="您的用户名">
                </div>
                
                <div class="form-group">
                  <label>电子邮箱</label>
                  <input type="email" v-model="userProfile.email" placeholder="您的电子邮箱">
                </div>
                
                <div class="form-group">
                  <label>手机号码</label>
                  <input type="tel" v-model="userProfile.phone" placeholder="您的手机号码">
                </div>
                
                <div class="form-group">
                  <label>自我介绍</label>
                  <textarea v-model="userProfile.bio" placeholder="简单介绍一下自己吧"></textarea>
                </div>
                
                <div class="form-actions">
                  <button class="btn-cancel">取消</button>
                  <button class="btn-save">保存修改</button>
                </div>
              </div>
            </div>
            
            <!-- 账号安全设置 -->
            <div v-if="activeSettingsSection === 1" class="settings-section">
              <h2 class="section-title">账号安全</h2>
              <div class="settings-card">
                <div class="form-group">
                  <label>当前密码</label>
                  <input type="password" v-model="security.currentPassword" placeholder="输入当前密码">
                </div>
                
                <div class="form-group">
                  <label>新密码</label>
                  <input type="password" v-model="security.newPassword" placeholder="输入新密码">
                </div>
                
                <div class="form-group">
                  <label>确认新密码</label>
                  <input type="password" v-model="security.confirmPassword" placeholder="再次输入新密码">
                </div>
                
                <div class="form-actions">
                  <button class="btn-save">修改密码</button>
                </div>
              </div>
              
              <div class="settings-card mt-20">
                <h3 class="card-title">两步验证</h3>
                <div class="toggle-option">
                  <div class="toggle-info">
                    <h4>启用两步验证</h4>
                    <p>提高账号安全性，登录时将要求额外的验证步骤</p>
                  </div>
                  <label class="toggle-switch">
                    <input type="checkbox" v-model="security.twoFactorEnabled">
                    <span class="toggle-slider"></span>
                  </label>
                </div>
              </div>
              
              <div class="settings-card mt-20">
                <h3 class="card-title">登录记录</h3>
                <div class="login-record" v-for="(record, index) in loginRecords" :key="index">
                  <div class="record-info">
                    <span class="material-icons">{{ record.device === '手机' ? 'smartphone' : 'computer' }}</span>
                    <div>
                      <h4>{{ record.location }}</h4>
                      <p>{{ record.time }} · {{ record.device }} · {{ record.browser }}</p>
                    </div>
                  </div>
                  <span :class="['record-status', record.current ? 'current' : '']">
                    {{ record.current ? '当前设备' : '' }}
                  </span>
                </div>
              </div>
            </div>
            
            <!-- 通知设置 -->
            <div v-if="activeSettingsSection === 2" class="settings-section">
              <h2 class="section-title">通知设置</h2>
              <div class="settings-card">
                <div class="toggle-option" v-for="(option, index) in notificationOptions" :key="index">
                  <div class="toggle-info">
                    <h4>{{ option.title }}</h4>
                    <p>{{ option.description }}</p>
                  </div>
                  <label class="toggle-switch">
                    <input type="checkbox" v-model="option.enabled">
                    <span class="toggle-slider"></span>
                  </label>
                </div>
              </div>
            </div>
            
            <!-- 外观偏好设置 -->
            <div v-if="activeSettingsSection === 3" class="settings-section">
              <h2 class="section-title">外观偏好</h2>
              <div class="settings-card">
                <div class="theme-option">
                  <h3 class="card-title">主题模式</h3>
                  <div class="theme-selector">
                    <div 
                      class="theme-item" 
                      :class="{ active: preferences.theme === 'light' }"
                      @click="setThemePreference('light')"
                    >
                      <div class="theme-preview light-theme"></div>
                      <span>浅色模式</span>
                    </div>
                    <div 
                      class="theme-item" 
                      :class="{ active: preferences.theme === 'dark' }"
                      @click="setThemePreference('dark')"
                    >
                      <div class="theme-preview dark-theme"></div>
                      <span>深色模式</span>
                    </div>
                    <div 
                      class="theme-item" 
                      :class="{ active: preferences.theme === 'auto' }"
                      @click="setThemePreference('auto')"
                    >
                      <div class="theme-preview auto-theme"></div>
                      <span>跟随系统</span>
                    </div>
                  </div>
                </div>
                
                <div class="font-option mt-20">
                  <h3 class="card-title">字体大小</h3>
                  <div class="slider-container">
                    <span class="material-icons">text_decrease</span>
                    <input 
                      type="range" 
                      min="80" 
                      max="120" 
                      step="10" 
                      v-model="preferences.fontSize"
                    >
                    <span class="material-icons">text_increase</span>
                  </div>
                  <div class="font-preview" :style="{ fontSize: preferences.fontSize + '%' }">
                    文字大小预览
                  </div>
                </div>
              </div>
            </div>
            
            <!-- 隐私设置 -->
            <div v-if="activeSettingsSection === 4" class="settings-section">
              <h2 class="section-title">隐私设置</h2>
              <div class="settings-card">
                <div class="toggle-option" v-for="(option, index) in privacyOptions" :key="index">
                  <div class="toggle-info">
                    <h4>{{ option.title }}</h4>
                    <p>{{ option.description }}</p>
                  </div>
                  <label class="toggle-switch">
                    <input type="checkbox" v-model="option.enabled">
                    <span class="toggle-slider"></span>
                  </label>
                </div>
              </div>
              
              <div class="settings-card mt-20">
                <h3 class="card-title">数据与隐私</h3>
                <div class="privacy-actions">
                  <button class="btn-outline">
                    <span class="material-icons">download</span>
                    导出我的数据
                  </button>
                  <button class="btn-outline danger">
                    <span class="material-icons">delete_forever</span>
                    删除我的账号
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
import { computed, onBeforeUnmount, onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';

export default {
  name: 'Settings',
  setup() {
    const router = useRouter();
    
    // 主题模式控制
    const isDarkMode = ref(localStorage.getItem('theme') === 'dark' || false);
    
    // 侧边栏状态控制
    const isSidebarCollapsed = ref(localStorage.getItem('sidebarCollapsed') === 'true' || false);
    const activeNavIndex = ref(10); // 设置中心的索引
    
    // 下拉菜单状态控制
    const isUserMenuVisible = ref(false);
    const isNotificationsVisible = ref(false);
    
    // 设置页面相关状态
    const activeSettingsSection = ref(0);
    
    // 设置页面导航项
    const settingsSections = ref([
      { title: '个人信息', icon: 'person' },
      { title: '账号安全', icon: 'security' },
      { title: '通知设置', icon: 'notifications' },
      { title: '外观偏好', icon: 'palette' },
      { title: '隐私设置', icon: 'lock' }
    ]);
    
    // 用户个人信息
    const userProfile = ref({
      username: '商家用户',
      email: 'merchant@example.com',
      phone: '138****1234',
      bio: '高级卖家，专注于电子产品销售与服务。'
    });
    
    // 安全设置
    const security = ref({
      currentPassword: '',
      newPassword: '',
      confirmPassword: '',
      twoFactorEnabled: false
    });
    
    // 登录记录
    const loginRecords = ref([
      {
        device: '电脑',
        browser: 'Chrome',
        location: '上海',
        time: '2023-10-25 15:30',
        current: true
      },
      {
        device: '手机',
        browser: 'Safari',
        location: '上海',
        time: '2023-10-24 09:15',
        current: false
      },
      {
        device: '电脑',
        browser: 'Firefox',
        location: '北京',
        time: '2023-10-20 18:45',
        current: false
      }
    ]);
    
    // 通知设置选项
    const notificationOptions = ref([
      {
        title: '新订单通知',
        description: '当有新订单时通过系统通知提醒',
        enabled: true
      },
      {
        title: '客户消息通知',
        description: '当收到客户消息时通过系统通知提醒',
        enabled: true
      },
      {
        title: '促销活动提醒',
        description: '当有新的促销活动时通过系统通知提醒',
        enabled: false
      },
      {
        title: '库存预警通知',
        description: '当商品库存不足时通过系统通知提醒',
        enabled: true
      },
      {
        title: '系统更新通知',
        description: '当系统有更新或维护时通过系统通知提醒',
        enabled: true
      },
      {
        title: '电子邮件通知',
        description: '接收所有通知的电子邮件副本',
        enabled: false
      }
    ]);
    
    // 外观偏好设置
    const preferences = ref({
      theme: isDarkMode.value ? 'dark' : 'light',
      fontSize: 100
    });
    
    // 隐私设置选项
    const privacyOptions = ref([
      {
        title: '数据分析',
        description: '允许系统分析您的使用数据以改进服务',
        enabled: true
      },
      {
        title: '个性化推荐',
        description: '根据您的使用习惯提供个性化的内容推荐',
        enabled: true
      },
      {
        title: '位置信息',
        description: '允许系统获取您的位置信息以提供本地化服务',
        enabled: false
      },
      {
        title: '第三方分享',
        description: '允许与合作的第三方服务共享您的使用数据',
        enabled: false
      }
    ]);
    
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
    
    // 当前页面标题
    const currentPageTitle = computed(() => {
      return navItems.value[activeNavIndex.value].title;
    });
    
    // 主题切换
    const toggleTheme = () => {
      isDarkMode.value = !isDarkMode.value;
      localStorage.setItem('theme', isDarkMode.value ? 'dark' : 'light');
      document.documentElement.classList.toggle('dark-theme', isDarkMode.value);
      
      // 同步更新偏好设置中的主题
      preferences.value.theme = isDarkMode.value ? 'dark' : 'light';
    };
    
    // 设置主题偏好
    const setThemePreference = (theme) => {
      preferences.value.theme = theme;
      if (theme === 'dark') {
        isDarkMode.value = true;
        localStorage.setItem('theme', 'dark');
        document.documentElement.classList.add('dark-theme');
      } else if (theme === 'light') {
        isDarkMode.value = false;
        localStorage.setItem('theme', 'light');
        document.documentElement.classList.remove('dark-theme');
      } else {
        // 自动模式，基于系统偏好
        const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
        isDarkMode.value = prefersDark;
        localStorage.setItem('theme', prefersDark ? 'dark' : 'light');
        document.documentElement.classList.toggle('dark-theme', prefersDark);
      }
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
      toggleTheme,
      toggleSidebar,
      setActiveNav,
      toggleUserMenu,
      toggleNotifications,
      logout,
      // 设置页面特有状态
      activeSettingsSection,
      settingsSections,
      userProfile,
      security,
      loginRecords,
      notificationOptions,
      preferences,
      privacyOptions,
      setThemePreference
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
    color: #f8df81;
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

  .dark-mode .user-info p {
    color: #aaa;
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

  /* 设置页面样式 */
  .settings-container {
    display: flex;
    max-width: 1200px;
    margin: 0 auto;
    height: 100%;
    gap: 25px;
  }

  .settings-sidebar {
    width: 240px;
    background: rgba(255, 255, 255, 0.9);
    backdrop-filter: blur(10px);
    border-radius: 15px;
    padding: 20px;
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.05);
    border: 1px solid rgba(0, 0, 0, 0.05);
    transition: all 0.3s ease;
    align-self: flex-start;
    position: sticky;
    top: 100px;
  }

  .dark-mode .settings-sidebar {
    background: rgba(31, 34, 41, 0.9);
    border: 1px solid rgba(255, 255, 255, 0.05);
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
  }

  .settings-nav-item {
    display: flex;
    align-items: center;
    padding: 12px 16px;
    border-radius: 10px;
    margin-bottom: 8px;
    cursor: pointer;
    transition: all 0.3s ease;
    color: #555;
  }

  .dark-mode .settings-nav-item {
    color: #aaa;
  }

  .settings-nav-item:hover {
    background: rgba(0, 0, 0, 0.04);
    color: #d4af37;
  }

  .dark-mode .settings-nav-item:hover {
    background: rgba(255, 255, 255, 0.04);
    color: #f8df81;
  }

  .settings-nav-item.active {
    background: linear-gradient(90deg, rgba(212, 175, 55, 0.15) 0%, rgba(212, 175, 55, 0.05) 100%);
    color: #d4af37;
    box-shadow: 0 4px 15px rgba(212, 175, 55, 0.1);
  }

  .dark-mode .settings-nav-item.active {
    background: linear-gradient(90deg, rgba(212, 175, 55, 0.2) 0%, rgba(212, 175, 55, 0.05) 100%);
    color: #f8df81;
  }

  .settings-nav-item .material-icons {
    margin-right: 12px;
    font-size: 22px;
  }

  .settings-content {
    flex: 1;
  }

  .settings-section {
    margin-bottom: 30px;
  }

  .section-title {
    font-size: 1.8rem;
    font-weight: 600;
    margin-bottom: 20px;
    color: #333;
  }

  .dark-mode .section-title {
    color: #f0f0f0;
  }

  .settings-card {
    background: rgba(255, 255, 255, 0.9);
    backdrop-filter: blur(10px);
    border-radius: 15px;
    padding: 25px;
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.05);
    border: 1px solid rgba(0, 0, 0, 0.05);
    transition: all 0.3s ease;
  }

  .dark-mode .settings-card {
    background: rgba(31, 34, 41, 0.9);
    border: 1px solid rgba(255, 255, 255, 0.05);
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
  }

  .card-title {
    font-size: 1.2rem;
    font-weight: 600;
    margin-bottom: 20px;
    color: #333;
  }

  .dark-mode .card-title {
    color: #f0f0f0;
  }

  .profile-header {
    display: flex;
    align-items: center;
    margin-bottom: 30px;
  }

  .profile-avatar {
    width: 100px;
    height: 100px;
    border-radius: 50%;
    overflow: hidden;
    position: relative;
    margin-right: 20px;
  }

  .profile-avatar img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }

  .avatar-upload {
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 30px;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;
    cursor: pointer;
    transition: all 0.3s ease;
  }

  .avatar-upload:hover {
    background: rgba(212, 175, 55, 0.8);
  }

  .profile-info h3 {
    font-size: 1.5rem;
    font-weight: 600;
    margin-bottom: 5px;
  }

  .profile-info p {
    color: #777;
    font-size: 0.9rem;
  }

  .dark-mode .profile-info p {
    color: #aaa;
  }

  .form-group {
    margin-bottom: 20px;
  }

  .form-group label {
    display: block;
    margin-bottom: 8px;
    font-weight: 500;
    color: #555;
  }

  .dark-mode .form-group label {
    color: #bbb;
  }

  .form-group input,
  .form-group textarea {
    width: 100%;
    padding: 12px 15px;
    border: 1px solid rgba(0, 0, 0, 0.1);
    border-radius: 8px;
    background: rgba(0, 0, 0, 0.02);
    color: #333;
    transition: all 0.3s ease;
  }

  .dark-mode .form-group input,
  .dark-mode .form-group textarea {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(255, 255, 255, 0.05);
    color: #f0f0f0;
  }

  .form-group input:focus,
  .form-group textarea:focus {
    outline: none;
    border-color: rgba(212, 175, 55, 0.5);
    box-shadow: 0 0 0 2px rgba(212, 175, 55, 0.2);
  }

  .form-group textarea {
    resize: vertical;
    min-height: 100px;
  }

  .form-actions {
    display: flex;
    justify-content: flex-end;
    gap: 15px;
    margin-top: 30px;
  }

  .btn-cancel,
  .btn-save,
  .btn-outline {
    padding: 10px 20px;
    border-radius: 8px;
    font-weight: 500;
    cursor: pointer;
    transition: all 0.3s ease;
  }

  .btn-cancel {
    background: rgba(0, 0, 0, 0.05);
    color: #555;
    border: none;
  }

  .dark-mode .btn-cancel {
    background: rgba(255, 255, 255, 0.05);
    color: #aaa;
  }

  .btn-cancel:hover {
    background: rgba(0, 0, 0, 0.1);
  }

  .dark-mode .btn-cancel:hover {
    background: rgba(255, 255, 255, 0.1);
  }

  .btn-save {
    background: linear-gradient(135deg, #d4af37, #f8df81);
    color: white;
    border: none;
  }

  .btn-save:hover {
    transform: translateY(-2px);
    box-shadow: 0 5px 15px rgba(212, 175, 55, 0.3);
  }

  .btn-outline {
    background: transparent;
    border: 1px solid rgba(0, 0, 0, 0.1);
    color: #555;
    display: flex;
    align-items: center;
    gap: 8px;
  }

  .dark-mode .btn-outline {
    border: 1px solid rgba(255, 255, 255, 0.1);
    color: #bbb;
  }

  .btn-outline:hover {
    border-color: #d4af37;
    color: #d4af37;
  }

  .dark-mode .btn-outline:hover {
    border-color: #f8df81;
    color: #f8df81;
  }

  .btn-outline.danger:hover {
    border-color: #dc3545;
    color: #dc3545;
  }

  .toggle-option {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 15px 0;
    border-bottom: 1px solid rgba(0, 0, 0, 0.05);
  }

  .dark-mode .toggle-option {
    border-bottom: 1px solid rgba(255, 255, 255, 0.05);
  }

  .toggle-option:last-child {
    border-bottom: none;
  }

  .toggle-info h4 {
    font-size: 16px;
    font-weight: 500;
    margin: 0 0 5px 0;
  }

  .toggle-info p {
    color: #777;
    font-size: 14px;
    margin: 0;
  }

  .dark-mode .toggle-info p {
    color: #aaa;
  }

  .toggle-switch {
    position: relative;
    display: inline-block;
    width: 50px;
    height: 26px;
  }

  .toggle-switch input {
    opacity: 0;
    width: 0;
    height: 0;
  }

  .toggle-slider {
    position: absolute;
    cursor: pointer;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: #ccc;
    transition: .4s;
    border-radius: 34px;
  }

  .toggle-slider:before {
    position: absolute;
    content: "";
    height: 18px;
    width: 18px;
    left: 4px;
    bottom: 4px;
    background-color: white;
    transition: .4s;
    border-radius: 50%;
  }

  input:checked + .toggle-slider {
    background-color: #d4af37;
  }

  input:checked + .toggle-slider:before {
    transform: translateX(24px);
  }

  .mt-20 {
    margin-top: 20px;
  }

  .login-record {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 12px 0;
    border-bottom: 1px solid rgba(0, 0, 0, 0.05);
  }

  .dark-mode .login-record {
    border-bottom: 1px solid rgba(255, 255, 255, 0.05);
  }

  .login-record:last-child {
    border-bottom: none;
  }

  .record-info {
    display: flex;
    align-items: center;
    gap: 12px;
  }

  .record-info .material-icons {
    color: #777;
    font-size: 22px;
  }

  .dark-mode .record-info .material-icons {
    color: #aaa;
  }

  .record-info h4 {
    font-size: 16px;
    font-weight: 500;
    margin: 0 0 5px 0;
  }

  .record-info p {
    color: #777;
    font-size: 13px;
    margin: 0;
  }

  .dark-mode .record-info p {
    color: #aaa;
  }

  .record-status {
    color: #777;
    font-size: 13px;
  }

  .dark-mode .record-status {
    color: #aaa;
  }

  .record-status.current {
    color: #28a745;
    font-weight: 500;
  }

  .theme-selector {
    display: flex;
    gap: 20px;
    margin-top: 15px;
  }

  .theme-item {
    text-align: center;
    cursor: pointer;
    transition: all 0.3s ease;
    border-radius: 10px;
    padding: 15px;
    border: 2px solid transparent;
  }

  .theme-item.active {
    border-color: #d4af37;
    background: rgba(212, 175, 55, 0.05);
  }

  .dark-mode .theme-item.active {
    background: rgba(212, 175, 55, 0.1);
  }

  .theme-preview {
    width: 80px;
    height: 80px;
    margin: 0 auto 10px;
    border-radius: 10px;
    border: 1px solid rgba(0, 0, 0, 0.1);
    position: relative;
    overflow: hidden;
  }

  .dark-mode .theme-preview {
    border: 1px solid rgba(255, 255, 255, 0.1);
  }

  .light-theme {
    background: linear-gradient(135deg, #f5f7fa 0%, #e4e9f0 100%);
  }

  .dark-theme {
    background: linear-gradient(135deg, #1f2229 0%, #121317 100%);
  }

  .auto-theme {
    background: linear-gradient(to right, #f5f7fa 0%, #e4e9f0 50%, #1f2229 50%, #121317 100%);
  }

  .theme-item span {
    font-size: 14px;
    color: #555;
  }

  .dark-mode .theme-item span {
    color: #bbb;
  }

  .slider-container {
    display: flex;
    align-items: center;
    gap: 10px;
    margin: 15px 0;
  }

  .slider-container input[type="range"] {
    flex: 1;
    -webkit-appearance: none;
    height: 5px;
    border-radius: 5px;
    background: #ddd;
    outline: none;
  }

  .dark-mode .slider-container input[type="range"] {
    background: #555;
  }

  .slider-container input[type="range"]::-webkit-slider-thumb {
    -webkit-appearance: none;
    appearance: none;
    width: 18px;
    height: 18px;
    border-radius: 50%;
    background: #d4af37;
    cursor: pointer;
  }

  .font-preview {
    background: rgba(0, 0, 0, 0.02);
    padding: 15px;
    border-radius: 8px;
    text-align: center;
  }

  .dark-mode .font-preview {
    background: rgba(255, 255, 255, 0.05);
  }

  .privacy-actions {
    display: flex;
    gap: 15px;
    margin-top: 15px;
  }

  /* 响应式设计 */
  @media (max-width: 992px) {
    .settings-container {
      flex-direction: column;
    }
    
    .settings-sidebar {
      width: 100%;
      position: static;
      display: flex;
      flex-wrap: wrap;
      gap: 10px;
      padding: 15px;
    }
    
    .settings-nav-item {
      flex: 1;
      min-width: 150px;
      margin-bottom: 0;
      justify-content: center;
    }
  }

  @media (max-width: 768px) {
    .settings-card {
      padding: 20px;
    }
    
    .profile-header {
      flex-direction: column;
      align-items: center;
      text-align: center;
    }
    
    .profile-avatar {
      margin-right: 0;
      margin-bottom: 15px;
    }
    
    .theme-selector {
      flex-wrap: wrap;
      justify-content: center;
    }
  }

  @media (max-width: 576px) {
    .form-actions {
      flex-direction: column-reverse;
    }
    
    .btn-cancel, .btn-save {
      width: 100%;
      text-align: center;
    }
    
    .privacy-actions {
      flex-direction: column;
    }
    
    .privacy-actions .btn-outline {
      width: 100%;
      justify-content: center;
    }
  }
</style> 