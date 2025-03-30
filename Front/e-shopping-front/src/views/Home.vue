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

      <!-- 内容区域 -->
      <div class="content-wrapper">
        <div class="dashboard-container">
          <!-- 欢迎卡片 -->
          <div class="welcome-card">
            <div class="welcome-content">
              <h2>欢迎回来，商家用户!</h2>
              <p>今天是 {{ currentDate }}，您的店铺正在蓬勃发展。</p>
              <div class="quick-stats">
                <div class="stat-item">
                  <span class="material-icons">trending_up</span>
                  <div class="stat-info">
                    <p class="stat-value">1,234</p>
                    <p class="stat-title">今日访问量</p>
                  </div>
                </div>
                <div class="stat-item">
                  <span class="material-icons">shopping_cart</span>
                  <div class="stat-info">
                    <p class="stat-value">56</p>
                    <p class="stat-title">今日订单数</p>
                  </div>
                </div>
                <div class="stat-item">
                  <span class="material-icons">payments</span>
                  <div class="stat-info">
                    <p class="stat-value">¥9,876</p>
                    <p class="stat-title">今日销售额</p>
                  </div>
                </div>
              </div>
            </div>
            <div class="welcome-decoration">
              <div class="decoration-circle"></div>
            </div>
          </div>

          <!-- 仪表盘卡片 -->
          <div class="dashboard-grid">
            <div class="dashboard-card sales-summary">
              <div class="card-header">
                <h3>销售概况</h3>
                <div class="card-actions">
                  <div class="dropdown">
                    <button class="dropdown-toggle">本周 <span class="material-icons">expand_more</span></button>
                  </div>
                </div>
              </div>
              <div class="card-body">
                <div class="sales-chart">
                  <!-- Chart placeholder -->
                  <div class="chart-placeholder">
                    <div class="bar" v-for="i in 7" :key="i" :style="{ height: (30 + Math.random() * 70) + '%' }"></div>
                  </div>
                </div>
              </div>
            </div>

            <div class="dashboard-card recent-orders">
              <div class="card-header">
                <h3>最近订单</h3>
                <div class="card-actions">
                  <span class="view-all">查看全部</span>
                </div>
              </div>
              <div class="card-body">
                <div class="order-list">
                  <div class="order-item" v-for="i in 3" :key="i">
                    <div class="order-info">
                      <div class="order-id">订单 #{{ 100000 + i }}</div>
                      <div class="order-details">
                        <span class="customer-name">客户{{ i }}</span>
                        <span class="order-date">{{ new Date().toLocaleDateString() }}</span>
                      </div>
                    </div>
                    <div class="order-amount">¥{{ 100 * i + 99 }}</div>
                    <div class="order-status" :class="i === 1 ? 'status-pending' : i === 2 ? 'status-processing' : 'status-completed'">
                      {{ i === 1 ? '待处理' : i === 2 ? '处理中' : '已完成' }}
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div class="dashboard-card inventory-warning">
              <div class="card-header">
                <h3>库存预警</h3>
                <div class="card-actions">
                  <span class="view-all">查看全部</span>
                </div>
              </div>
              <div class="card-body">
                <div class="inventory-list">
                  <div class="inventory-item" v-for="i in 3" :key="i">
                    <div class="product-thumbnail"></div>
                    <div class="product-info">
                      <div class="product-name">产品名称 {{ i }}</div>
                      <div class="product-sku">SKU-{{ 1000 + i }}</div>
                    </div>
                    <div class="inventory-status">
                      <div class="inventory-bar">
                        <div class="inventory-progress" :style="{ width: (10 * i) + '%' }"></div>
                      </div>
                      <div class="inventory-count">{{ i * 5 }} / 100</div>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div class="dashboard-card top-products">
              <div class="card-header">
                <h3>热销商品</h3>
                <div class="card-actions">
                  <div class="dropdown">
                    <button class="dropdown-toggle">本月 <span class="material-icons">expand_more</span></button>
                  </div>
                </div>
              </div>
              <div class="card-body">
                <div class="product-list">
                  <div class="product-item" v-for="i in 3" :key="i">
                    <div class="rank-badge">{{ i }}</div>
                    <div class="product-thumbnail"></div>
                    <div class="product-info">
                      <div class="product-name">热销商品 {{ i }}</div>
                      <div class="product-sales">{{ 100 - (i * 20) }}件已售</div>
                    </div>
                    <div class="sales-percentage">
                      <div class="percentage-value">{{ 25 - (i * 5) }}%</div>
                      <span class="material-icons trending-icon">trending_up</span>
                    </div>
                  </div>
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
  name: 'Home',
  setup() {
    const router = useRouter();
    
    // 主题模式控制
    const isDarkMode = ref(localStorage.getItem('theme') === 'dark' || false);
    
    // 侧边栏状态控制
    const isSidebarCollapsed = ref(localStorage.getItem('sidebarCollapsed') === 'true' || false);
    const activeNavIndex = ref(0);
    
    // 下拉菜单状态控制
    const isUserMenuVisible = ref(false);
    const isNotificationsVisible = ref(false);
    
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
    
    // 当前日期格式化
    const currentDate = computed(() => {
      const date = new Date();
      const year = date.getFullYear();
      const month = date.getMonth() + 1;
      const day = date.getDate();
      const weekday = ['周日', '周一', '周二', '周三', '周四', '周五', '周六'][date.getDay()];
      return `${year}年${month}月${day}日 ${weekday}`;
    });
    
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
      currentDate,
      toggleTheme,
      toggleSidebar,
      setActiveNav,
      toggleUserMenu,
      toggleNotifications,
      logout
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
  background: linear-gradient(135deg, #d4af37 0%, #f8df81 100%);
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


.dark-mode .dropdown-footer {
  border-top: 1px solid rgba(255, 255, 255, 0.05);
  color: #f8df81;
}

/* 内容区域 */
.content-wrapper {
  flex: 1;
  padding: 30px 0;
}

.dashboard-container {
  max-width: 1400px;
  margin: 0 auto;
}

/* 欢迎卡片 */
.welcome-card {
  background: linear-gradient(135deg, rgba(255, 255, 255, 0.9) 0%, rgba(245, 245, 245, 0.8) 100%);
  backdrop-filter: blur(10px);
  border-radius: 20px;
  padding: 30px;
  margin-bottom: 30px;
  display: flex;
  position: relative;
  overflow: hidden;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.05);
  border: 1px solid rgba(0, 0, 0, 0.05);
}

.dark-mode .welcome-card {
  background: linear-gradient(135deg, rgba(31, 34, 41, 0.9) 0%, rgba(20, 21, 26, 0.8) 100%);
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
  border: 1px solid rgba(255, 255, 255, 0.05);
}

.welcome-content {
  flex: 1;
  z-index: 1;
}

.welcome-content h2 {
  font-size: 1.8rem;
  font-weight: 700;
  margin-bottom: 10px;
  background: linear-gradient(to right, #333, #555);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.dark-mode .welcome-content h2 {
  background: linear-gradient(to right, #f0f0f0, #d0d0d0);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.welcome-content p {
  color: #666;
  margin-bottom: 20px;
  font-size: 1rem;
}

.dark-mode .welcome-content p {
  color: #aaa;
}

.quick-stats {
  display: flex;
  gap: 30px;
  margin-top: 20px;
}

.stat-item {
  display: flex;
  align-items: center;
  background: rgba(255, 255, 255, 0.8);
  padding: 16px;
  border-radius: 16px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
}

.dark-mode .stat-item {
  background: rgba(37, 40, 48, 0.8);
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}

.stat-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
}

.stat-item .material-icons {
  font-size: 24px;
  margin-right: 16px;
  color: #d4af37;
  background: rgba(212, 175, 55, 0.1);
  padding: 8px;
  border-radius: 12px;
}

.stat-info {
  display: flex;
  flex-direction: column;
}

.stat-value {
  font-size: 1.5rem;
  font-weight: 700;
  color: #333;
  margin: 0;
}

.dark-mode .stat-value {
  color: #f0f0f0;
}

.stat-title {
  font-size: 0.875rem;
  color: #777;
  margin: 0;
}

.dark-mode .stat-title {
  color: #999;
}

.welcome-decoration {
  position: absolute;
  top: 0;
  right: 0;
  width: 300px;
  height: 100%;
  overflow: hidden;
}

.decoration-circle {
  position: absolute;
  width: 400px;
  height: 400px;
  border-radius: 50%;
  background: radial-gradient(circle, rgba(212, 175, 55, 0.1) 0%, rgba(212, 175, 55, 0.05) 70%);
  top: -100px;
  right: -100px;
  animation: pulse 5s infinite ease-in-out;
}

@keyframes pulse {
  0% {
    transform: scale(1);
    opacity: 0.8;
  }
  50% {
    transform: scale(1.1);
    opacity: 0.6;
  }
  100% {
    transform: scale(1);
    opacity: 0.8;
  }
}

/* 仪表盘卡片网格 */
.dashboard-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 30px;
  margin-top: 30px;
}

.dashboard-card {
  background: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(10px);
  border-radius: 20px;
  overflow: hidden;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
  border: 1px solid rgba(0, 0, 0, 0.05);
  height: 100%;
  display: flex;
  flex-direction: column;
}

.dark-mode .dashboard-card {
  background: rgba(31, 34, 41, 0.9);
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
  border: 1px solid rgba(255, 255, 255, 0.05);
}

.dashboard-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
}

.dark-mode .dashboard-card:hover {
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.25);
}

.card-header {
  padding: 20px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid rgba(0, 0, 0, 0.05);
}

.dark-mode .card-header {
  border-bottom: 1px solid rgba(255, 255, 255, 0.05);
}

.card-header h3 {
  font-size: 18px;
  font-weight: 600;
  margin: 0;
}

.card-actions {
  display: flex;
  align-items: center;
}

.dropdown-toggle {
  display: flex;
  align-items: center;
  font-size: 14px;
  background: transparent;
  border: none;
  cursor: pointer;
  color: #666;
  padding: 5px 10px;
  border-radius: 5px;
  transition: all 0.2s ease;
}

.dark-mode .dropdown-toggle {
  color: #aaa;
}

.dropdown-toggle:hover {
  background: rgba(0, 0, 0, 0.04);
  color: #d4af37;
}

.dark-mode .dropdown-toggle:hover {
  background: rgba(255, 255, 255, 0.04);
  color: #f8df81;
}

.dropdown-toggle .material-icons {
  font-size: 16px;
  margin-left: 5px;
}

.card-body {
  padding: 20px;
  flex: 1;
  display: flex;
  flex-direction: column;
}

.view-all {
  font-size: 14px;
  color: #d4af37;
  cursor: pointer;
  transition: all 0.2s ease;
}

.dark-mode .view-all {
  color: #f8df81;
}

.view-all:hover {
  text-decoration: underline;
}

/* 图表占位样式 */
.chart-placeholder {
  height: 200px;
  display: flex;
  align-items: flex-end;
  gap: 10px;
  margin-top: 20px;
}

.bar {
  flex: 1;
  background: linear-gradient(to top, #d4af37, #f8df81);
  border-radius: 4px 4px 0 0;
  min-height: 30%;
  animation: growUp 1s ease;
}

@keyframes growUp {
  from { height: 0; }
}

/* 订单列表样式 */
.order-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.order-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 15px;
  background: rgba(255, 255, 255, 0.5);
  border-radius: 12px;
  transition: all 0.2s ease;
}

.dark-mode .order-item {
  background: rgba(37, 40, 48, 0.5);
}

.order-item:hover {
  background: rgba(255, 255, 255, 0.8);
  transform: translateX(5px);
}

.dark-mode .order-item:hover {
  background: rgba(37, 40, 48, 0.8);
}

.order-info {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.order-id {
  font-weight: 600;
  font-size: 14px;
}

.order-details {
  display: flex;
  gap: 10px;
  font-size: 13px;
  color: #666;
}

.dark-mode .order-details {
  color: #aaa;
}

.order-amount {
  font-weight: 700;
  font-size: 16px;
  color: #d4af37;
}

.order-status {
  padding: 5px 10px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 600;
  text-align: center;
  min-width: 80px;
}

.status-pending {
  background: rgba(255, 193, 7, 0.1);
  color: #ffc107;
}

.status-processing {
  background: rgba(13, 110, 253, 0.1);
  color: #0d6efd;
}

.status-completed {
  background: rgba(25, 135, 84, 0.1);
  color: #198754;
}

/* 库存列表样式 */
.inventory-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.inventory-item {
  display: flex;
  align-items: center;
  gap: 15px;
  padding: 15px;
  background: rgba(255, 255, 255, 0.5);
  border-radius: 12px;
  transition: all 0.2s ease;
}

.dark-mode .inventory-item {
  background: rgba(37, 40, 48, 0.5);
}

.inventory-item:hover {
  background: rgba(255, 255, 255, 0.8);
  transform: translateX(5px);
}

.dark-mode .inventory-item:hover {
  background: rgba(37, 40, 48, 0.8);
}

.product-thumbnail {
  width: 40px;
  height: 40px;
  background: #f0f0f0;
  border-radius: 8px;
  overflow: hidden;
}

.dark-mode .product-thumbnail {
  background: #2a2d36;
}

.product-info {
  flex: 1;
}

.product-name {
  font-weight: 600;
  font-size: 14px;
  margin-bottom: 3px;
}

.product-sku {
  font-size: 12px;
  color: #777;
}

.dark-mode .product-sku {
  color: #999;
}

.inventory-status {
  width: 120px;
}

.inventory-bar {
  height: 6px;
  background: rgba(0, 0, 0, 0.1);
  border-radius: 3px;
  overflow: hidden;
  margin-bottom: 5px;
}

.dark-mode .inventory-bar {
  background: rgba(255, 255, 255, 0.1);
}

.inventory-progress {
  height: 100%;
  background: linear-gradient(to right, #dc3545, #ffc107);
  border-radius: 3px;
}

.inventory-count {
  font-size: 12px;
  color: #666;
  text-align: right;
}

.dark-mode .inventory-count {
  color: #aaa;
}

/* 热销商品列表 */
.product-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.product-item {
  display: flex;
  align-items: center;
  gap: 15px;
  padding: 15px;
  background: rgba(255, 255, 255, 0.5);
  border-radius: 12px;
  transition: all 0.2s ease;
  position: relative;
}

.dark-mode .product-item {
  background: rgba(37, 40, 48, 0.5);
}

.product-item:hover {
  background: rgba(255, 255, 255, 0.8);
  transform: translateX(5px);
}

.dark-mode .product-item:hover {
  background: rgba(37, 40, 48, 0.8);
}

.rank-badge {
  position: absolute;
  top: -5px;
  left: -5px;
  width: 24px;
  height: 24px;
  border-radius: 50%;
  background: #d4af37;
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 12px;
  font-weight: 700;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
}

.sales-percentage {
  display: flex;
  align-items: center;
  gap: 5px;
}

.percentage-value {
  font-weight: 700;
  color: #198754;
}

.trending-icon {
  color: #198754;
  font-size: 16px;
}

/* 响应式布局调整 */
@media (max-width: 1200px) {
  .dashboard-grid {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .quick-stats {
    flex-wrap: wrap;
    gap: 15px;
  }
  
  .stat-item {
    flex: 1;
    min-width: calc(50% - 15px);
  }
}

@media (max-width: 992px) {
  .welcome-card {
    flex-direction: column;
  }
  
  .welcome-decoration {
    position: relative;
    width: 100%;
    height: 100px;
    margin-top: 20px;
  }
  
  .decoration-circle {
    top: -150px;
    right: auto;
    left: 50%;
    transform: translateX(-50%);
  }
  
  .search-box {
    width: 200px;
  }
}

@media (max-width: 768px) {
  .dashboard-grid {
    grid-template-columns: 1fr;
  }
  
  .sidebar {
    position: fixed;
    z-index: 1000;
    transform: translateX(-100%);
    transition: transform 0.3s ease;
  }
  
  .sidebar.active {
    transform: translateX(0);
  }
  
  .main-content {
    padding: 0 10px;
  }
  
  .top-header {
    padding: 0 10px;
  }
  
  .page-title h2 {
    font-size: 1.5rem;
  }
  
  .search-box {
    width: 160px;
  }
  
  .quick-stats {
    flex-direction: column;
  }
  
  .stat-item {
    width: 100%;
  }
}

@media (max-width: 576px) {
  .welcome-card {
    padding: 20px;
  }
  
  .search-box {
    display: none;
  }
  
  .header-right {
    gap: 10px;
  }
  
  .order-item, .inventory-item, .product-item {
    flex-direction: column;
    align-items: flex-start;
  }
  
  .order-status, .inventory-status, .sales-percentage {
    margin-top: 10px;
    align-self: flex-end;
  }
  
  .notification .dropdown-menu {
    width: 280px;
  }
}

</style>