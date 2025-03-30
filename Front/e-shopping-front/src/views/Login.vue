<template>
  <div class="login-container">
    <div class="background-shapes">
      <div class="shape shape-1"></div>
      <div class="shape shape-2"></div>
      <div class="shape shape-3"></div>
      <div class="shape shape-4"></div>
    </div>
    <div class="login-content">
      <div class="login-left">
        <div class="brand-container">
          <h1 class="brand-name">ES-Shopping</h1>
          <p class="brand-tagline">卖家管理平台</p>
        </div>
        <div class="value-props">
          <h2 class="value-title">一站式电商解决方案</h2>
          <div class="value-features">
            <div class="feature">
              <div class="feature-icon"><span class="material-icon">inventory_2</span></div>
              <div class="feature-text">高效商品管理</div>
            </div>
            <div class="feature">
              <div class="feature-icon"><span class="material-icon">receipt_long</span></div>
              <div class="feature-text">便捷订单处理</div>
            </div>
            <div class="feature">
              <div class="feature-icon"><span class="material-icon">people</span></div>
              <div class="feature-text">全面客户关系</div>
            </div>
            <div class="feature">
              <div class="feature-icon"><span class="material-icon">analytics</span></div>
              <div class="feature-text">实时数据分析</div>
            </div>
          </div>
        </div>
        <div class="brand-quote">
          <p>"助力商家数字化转型，实现业务增长"</p>
        </div>
      </div>
      <div class="login-right">
        <div class="login-form-container">
          <div class="form-header">
            <h2 class="form-title">欢迎回来</h2>
            <p class="form-subtitle">请登录您的账户访问管理中心</p>
          </div>
          <form class="login-form" @submit.prevent="handleLogin">
            <div class="form-group" :class="{ 'focused': activeField === 'username', 'filled': username }">
              <label for="username">用户名</label>
              <div class="input-container">
                <span class="input-icon"><span class="material-icon">person</span></span>
                <input 
                  type="text" 
                  id="username"
                  v-model="username"
                  @focus="setActiveField('username')"
                  @blur="setActiveField(null)"
                  autocomplete="username"
                >
              </div>
            </div>
            <div class="form-group" :class="{ 'focused': activeField === 'password', 'filled': password }">
              <label for="password">密码</label>
              <div class="input-container">
                <span class="input-icon"><span class="material-icon">lock</span></span>
                <input 
                  :type="showPassword ? 'text' : 'password'" 
                  id="password"
                  v-model="password"
                  @focus="setActiveField('password')"
                  @blur="setActiveField(null)"
                  autocomplete="current-password"
                >
                <button 
                  type="button" 
                  class="toggle-password"
                  @click="togglePasswordVisibility"
                  aria-label="Toggle password visibility"
                >
                  <span class="material-icon">{{ showPassword ? 'visibility_off' : 'visibility' }}</span>
                </button>
              </div>
            </div>
            
            <div v-if="errorMessage" class="error-message">
              <span class="material-icon">error_outline</span>
              <span>{{ errorMessage }}</span>
            </div>
            
            <div class="form-options">
              <label class="remember-me">
                <div class="custom-checkbox">
                  <input type="checkbox" v-model="rememberMe" id="remember">
                  <span class="checkmark"></span>
                </div>
                <span class="checkbox-label">记住我</span>
              </label>
              <a href="#" class="forgot-password">忘记密码?</a>
            </div>
            <button type="submit" class="login-button" :disabled="isLoading">
              <span v-if="isLoading" class="loader"></span>
              <span v-else>登录</span>
            </button>
            <div class="login-divider">
              <span>或</span>
            </div>
            <div class="alt-login-options">
              <button type="button" class="alt-login-btn sso-login">
                <span class="material-icon">domain</span>
                <span>企业单点登录</span>
              </button>
            </div>
          </form>
          <div class="login-footer">
            <p>还没有账户? <router-link to="/register" class="register-link">注册账号</router-link></p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'LoginView',
  data() {
    return {
      username: '',
      password: '',
      rememberMe: false,
      showPassword: false,
      isLoading: false,
      activeField: null,
      errorMessage: ''
    }
  },
  mounted() {
    document.title = "ES-Shopping 卖家管理平台 - 登录";
    this.addGlassMorphismEffect();
    window.addEventListener('mousemove', this.handleMouseMove);
    
    // 如果有记住的用户名，自动填充
    const rememberedUser = localStorage.getItem('rememberedUser');
    if (rememberedUser) {
      this.username = rememberedUser;
      this.rememberMe = true;
    }
  },
  beforeUnmount() {
    window.removeEventListener('mousemove', this.handleMouseMove);
  },
  methods: {
    handleLogin() {
      // 表单验证
      if (!this.username || !this.password) {
        this.errorMessage = '请输入用户名和密码';
        return;
      }
      
      this.isLoading = true;
      this.errorMessage = '';
      
      // 使用axios发送登录请求
      axios.post('/admin/user/login', {
        username: this.username,
        password: this.password
      })
      .then(response => {
        this.isLoading = false;
        const data = response.data;
        
        if (data.code === 200) {
          // 登录成功，存储token和用户信息
          localStorage.setItem('token', data.data.token);
          localStorage.setItem('userName', data.data.name);
          localStorage.setItem('userId', data.data.id);
          localStorage.setItem('userUsername', data.data.username);
          
          // 将token添加到axios全局默认请求头中
          axios.defaults.headers.common['Authorization'] = `Bearer ${data.data.token}`;
          
          // 如果选择了记住我，可以额外存储一些信息
          if (this.rememberMe) {
            localStorage.setItem('rememberedUser', this.username);
          } else {
            localStorage.removeItem('rememberedUser');
          }
          
          // 跳转到首页
          this.$router.push('/home');
        } else {
          // 登录失败，显示错误信息
          this.errorMessage = data.msg || '登录失败，请检查用户名和密码';
        }
      })
      .catch(error => {
        this.isLoading = false;
        
        if (error.response) {
          // 服务器返回了错误状态码
          this.errorMessage = error.response.data.message || `服务器错误 (${error.response.status})`;
        } else if (error.request) {
          // 请求发出但没有收到响应
          this.errorMessage = '无法连接到服务器，请检查网络连接';
        } else {
          // 请求设置时出错
          this.errorMessage = '请求错误，请稍后重试';
        }
        
        console.error('登录请求错误:', error);
      });
    },
    togglePasswordVisibility() {
      this.showPassword = !this.showPassword;
    },
    setActiveField(field) {
      this.activeField = field;
    },
    addGlassMorphismEffect() {
      // 为背景形状添加随机位置
      const shapes = document.querySelectorAll('.shape');
      shapes.forEach(shape => {
        const randomDelay = Math.random() * 2;
        shape.style.animationDelay = `${randomDelay}s`;
      });
    },
    handleMouseMove(event) {
      // 视差效果
      const shapes = document.querySelectorAll('.shape');
      const x = event.clientX / window.innerWidth;
      const y = event.clientY / window.innerHeight;
      
      shapes.forEach((shape, index) => {
        const factor = (index + 1) * 15;
        const moveX = (x - 0.5) * factor;
        const moveY = (y - 0.5) * factor;
        shape.style.transform = `translate(${moveX}px, ${moveY}px)`;
      });
    }
  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap');
@import url('https://fonts.googleapis.com/icon?family=Material+Icons');

/* 全局样式重置 */
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
  font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
}

.material-icon {
  font-family: 'Material Icons';
  font-weight: normal;
  font-style: normal;
  font-size: 24px;
  line-height: 1;
  letter-spacing: normal;
  text-transform: none;
  display: inline-block;
  white-space: nowrap;
  word-wrap: normal;
  direction: ltr;
  -webkit-font-feature-settings: 'liga';
  -webkit-font-smoothing: antialiased;
}

.login-container {
  width: 100%;
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(145deg, #0a0a0a 0%, #121212 100%);
  padding: 20px;
  position: relative;
  overflow: hidden;
}

/* 背景动画效果 */
.background-shapes {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  overflow: hidden;
  z-index: 0;
}

.shape {
  position: absolute;
  background: linear-gradient(145deg, rgba(212, 175, 55, 0.15), rgba(173, 137, 18, 0.05));
  border-radius: 50%;
  backdrop-filter: blur(4px);
  -webkit-backdrop-filter: blur(4px);
  transition: transform 0.4s ease-out;
}

.shape-1 {
  width: 400px;
  height: 400px;
  top: -150px;
  right: -100px;
  animation: floatAnimation 12s ease-in-out infinite alternate;
}

.shape-2 {
  width: 300px;
  height: 300px;
  bottom: -100px;
  left: -100px;
  animation: floatAnimation 10s ease-in-out infinite alternate-reverse;
}

.shape-3 {
  width: 200px;
  height: 200px;
  top: 30%;
  left: 15%;
  animation: floatAnimation 14s ease-in-out infinite alternate;
}

.shape-4 {
  width: 250px;
  height: 250px;
  bottom: 20%;
  right: 15%;
  animation: floatAnimation 16s ease-in-out infinite alternate-reverse;
}

@keyframes floatAnimation {
  0% {
    transform: translate(0, 0) rotate(0deg);
  }
  100% {
    transform: translate(30px, 30px) rotate(10deg);
  }
}

.login-content {
  width: 100%;
  max-width: 1200px;
  min-height: 660px;
  display: flex;
  border-radius: 24px;
  overflow: hidden;
  box-shadow: 0 25px 100px rgba(0, 0, 0, 0.5);
  background-color: rgba(18, 18, 18, 0.6);
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
  border: 1px solid rgba(212, 175, 55, 0.12);
  position: relative;
  z-index: 1;
}

.login-left {
  flex: 1;
  background: linear-gradient(135deg, #151515 0%, #0a0a0a 100%);
  color: white;
  padding: 60px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  position: relative;
  overflow: hidden;
  clip-path: polygon(0 0, 100% 0, 92% 100%, 0% 100%);
  border-right: 1px solid rgba(212, 175, 55, 0.2);
}

.login-left::before {
  content: '';
  position: absolute;
  width: 300px;
  height: 300px;
  border-radius: 50%;
  background: linear-gradient(145deg, rgba(212, 175, 55, 0.1), rgba(173, 137, 18, 0.05));
  top: -100px;
  right: -100px;
  backdrop-filter: blur(5px);
  -webkit-backdrop-filter: blur(5px);
}

.login-left::after {
  content: '';
  position: absolute;
  width: 200px;
  height: 200px;
  border-radius: 50%;
  background: linear-gradient(145deg, rgba(212, 175, 55, 0.08), rgba(173, 137, 18, 0.03));
  bottom: -50px;
  left: -50px;
  backdrop-filter: blur(5px);
  -webkit-backdrop-filter: blur(5px);
}

.brand-container {
  z-index: 1;
  position: relative;
}

.brand-name {
  font-size: 2.8rem;
  font-weight: 700;
  margin: 0;
  letter-spacing: -0.5px;
  text-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
  animation: fadeInDown 0.6s ease-out;
  background: linear-gradient(to right, #d4af37, #f8df81, #d4af37);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  position: relative;
}

.brand-name::after {
  content: '';
  position: absolute;
  bottom: -8px;
  left: 0;
  width: 60px;
  height: 3px;
  background: linear-gradient(to right, #d4af37, #f8df81);
  border-radius: 3px;
}

.brand-tagline {
  font-size: 1.3rem;
  opacity: 0.9;
  margin-top: 16px;
  animation: fadeInDown 0.6s ease-out 0.2s both;
  font-weight: 300;
  letter-spacing: 0.5px;
  color: #c0c0c0;
}

.value-props {
  z-index: 1;
  position: relative;
  animation: fadeIn 0.8s ease-out 0.4s both;
}

.value-title {
  font-size: 2rem;
  font-weight: 600;
  margin-bottom: 36px;
  line-height: 1.3;
  text-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  background: linear-gradient(to right, #d4af37, #e6c66e);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.value-features {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 30px 20px;
}

.feature {
  display: flex;
  align-items: center;
  opacity: 0;
  animation: fadeInRight 0.5s ease-out forwards;
  backdrop-filter: blur(5px);
  -webkit-backdrop-filter: blur(5px);
  background: rgba(20, 20, 20, 0.8);
  padding: 16px;
  border-radius: 16px;
  box-shadow: 0 4px 30px rgba(0, 0, 0, 0.3);
  border: 1px solid rgba(212, 175, 55, 0.15);
  transition: all 0.3s ease;
}

.feature:hover {
  transform: translateY(-5px);
  background: rgba(25, 25, 25, 0.8);
  border: 1px solid rgba(212, 175, 55, 0.3);
  box-shadow: 0 8px 30px rgba(0, 0, 0, 0.4), 0 0 15px rgba(212, 175, 55, 0.2);
}

.feature:nth-child(1) { animation-delay: 0.6s; }
.feature:nth-child(2) { animation-delay: 0.8s; }
.feature:nth-child(3) { animation-delay: 1s; }
.feature:nth-child(4) { animation-delay: 1.2s; }

.feature-icon {
  margin-right: 12px;
  width: 48px;
  height: 48px;
  border-radius: 14px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(145deg, #1a1a1a, #0f0f0f);
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.3);
  border: 1px solid rgba(212, 175, 55, 0.15);
}

.feature-icon .material-icon {
  font-size: 26px;
  background: linear-gradient(to bottom, #d4af37, #e5c975);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.feature-text {
  font-weight: 500;
  font-size: 1.1rem;
  color: #e0e0e0;
}

.brand-quote {
  margin-top: 40px;
  position: relative;
  z-index: 1;
  font-style: italic;
  font-weight: 300;
  font-size: 1.1rem;
  color: #bdbdbd;
  line-height: 1.6;
  padding-left: 16px;
  border-left: 3px solid rgba(212, 175, 55, 0.5);
}

.login-right {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px;
  backdrop-filter: blur(16px);
  -webkit-backdrop-filter: blur(16px);
  background: rgba(18, 18, 18, 0.9);
}

.login-form-container {
  width: 85%;
  max-width: 440px;
  animation: fadeIn 0.8s ease-out;
}

.form-header {
  margin-bottom: 40px;
  text-align: center;
}

.form-title {
  font-size: 2.2rem;
  font-weight: 700;
  margin-bottom: 12px;
  background: linear-gradient(to right, #d4af37, #f8df81, #d4af37);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  text-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
}

.form-subtitle {
  color: #9e9e9e;
  font-size: 1.1rem;
  font-weight: 400;
}

.login-form {
  display: flex;
  flex-direction: column;
  gap: 28px;
}

.form-group {
  position: relative;
}

.form-group label {
  display: block;
  margin-bottom: 10px;
  color: #bbbbbb;
  font-size: 0.95rem;
  font-weight: 500;
  transition: all 0.2s ease;
}

.input-container {
  position: relative;
  display: flex;
  align-items: center;
}

.input-icon {
  position: absolute;
  left: 16px;
  color: #9e9e9e;
  display: flex;
  align-items: center;
  justify-content: center;
}

.input-icon .material-icon {
  font-size: 20px;
}

.form-group input {
  width: 100%;
  padding: 16px 16px 16px 48px;
  border: 1px solid #333333;
  border-radius: 14px;
  font-size: 1rem;
  transition: all 0.3s ease;
  background: rgba(25, 25, 25, 0.5);
  color: #e0e0e0;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.form-group.focused input {
  outline: none;
  border-color: #d4af37;
  box-shadow: 0 0 0 4px rgba(212, 175, 55, 0.15);
  background: rgba(28, 28, 28, 0.7);
}

.toggle-password {
  position: absolute;
  right: 16px;
  top: 50%;
  transform: translateY(-50%);
  border: none;
  background: none;
  color: #9e9e9e;
  cursor: pointer;
  padding: 0;
  display: flex;
  align-items: center;
  justify-content: center;
}

.toggle-password:hover {
  color: #d4af37;
}

.form-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 0.95rem;
}

.remember-me {
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
}

.custom-checkbox {
  position: relative;
  display: inline-block;
  width: 20px;
  height: 20px;
}

.custom-checkbox input {
  position: absolute;
  opacity: 0;
  cursor: pointer;
  height: 0;
  width: 0;
}

.checkmark {
  position: absolute;
  top: 0;
  left: 0;
  height: 20px;
  width: 20px;
  background-color: rgba(30, 30, 30, 0.8);
  border: 1px solid #333333;
  border-radius: 6px;
  transition: all 0.2s ease;
}

.custom-checkbox input:checked ~ .checkmark {
  background-color: #d4af37;
  border-color: #d4af37;
}

.checkmark:after {
  content: "";
  position: absolute;
  display: none;
  left: 7px;
  top: 3px;
  width: 5px;
  height: 10px;
  border: solid #0a0a0a;
  border-width: 0 2px 2px 0;
  transform: rotate(45deg);
}

.custom-checkbox input:checked ~ .checkmark:after {
  display: block;
}

.checkbox-label {
  color: #bbbbbb;
  font-weight: 500;
}

.forgot-password {
  color: #d4af37;
  text-decoration: none;
  font-weight: 500;
  transition: all 0.2s ease;
}

.forgot-password:hover {
  color: #f8df81;
  text-decoration: underline;
}

.login-button {
  margin-top: 6px;
  padding: 16px;
  background: linear-gradient(135deg, #d4af37 0%, #b8860b 100%);
  color: #000000;
  border: none;
  border-radius: 14px;
  font-size: 1.05rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 6px 16px rgba(212, 175, 55, 0.3);
}

.login-button::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(
    90deg,
    rgba(255, 255, 255, 0),
    rgba(255, 255, 255, 0.3),
    rgba(255, 255, 255, 0)
  );
  transition: all 0.6s ease;
}

.login-button:hover {
  transform: translateY(-3px);
  box-shadow: 0 8px 25px rgba(212, 175, 55, 0.4);
}

.login-button:hover::before {
  left: 100%;
}

.login-button:disabled {
  background: linear-gradient(135deg, #4a4a4a 0%, #2a2a2a 100%);
  color: #888888;
  cursor: not-allowed;
  transform: none;
  box-shadow: none;
}

.login-button:active:not(:disabled) {
  transform: translateY(0);
  box-shadow: 0 4px 12px rgba(212, 175, 55, 0.25);
}

.error-message {
  display: flex;
  align-items: center;
  padding: 12px;
  background: rgba(220, 53, 69, 0.1);
  color: #dc3545;
  border-radius: 10px;
  margin-bottom: 20px;
  font-size: 0.95rem;
  border-left: 3px solid #dc3545;
}

.error-message .material-icon {
  margin-right: 10px;
  font-size: 20px;
}

.loader {
  display: inline-block;
  width: 24px;
  height: 24px;
  border: 3px solid rgba(0, 0, 0, 0.2);
  border-radius: 50%;
  border-top-color: #000000;
  animation: spin 1s ease-in-out infinite;
}

.login-divider {
  display: flex;
  align-items: center;
  color: #757575;
  font-size: 0.9rem;
  margin: 6px 0;
}

.login-divider::before,
.login-divider::after {
  content: "";
  flex: 1;
  border-bottom: 1px solid #333333;
}

.login-divider::before {
  margin-right: 15px;
}

.login-divider::after {
  margin-left: 15px;
}

.alt-login-options {
  display: flex;
  justify-content: center;
  gap: 15px;
}

.alt-login-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  padding: 14px 20px;
  border-radius: 14px;
  background: rgba(25, 25, 25, 0.8);
  border: 1px solid #333333;
  color: #bbbbbb;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
  width: 100%;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.alt-login-btn:hover {
  border-color: #d4af37;
  background: rgba(28, 28, 28, 0.9);
  transform: translateY(-2px);
}

.alt-login-btn .material-icon {
  font-size: 20px;
  color: #d4af37;
}

.login-footer {
  margin-top: 30px;
  text-align: center;
  color: #9e9e9e;
  font-size: 0.95rem;
}

.login-footer a {
  color: #d4af37;
  text-decoration: none;
  font-weight: 500;
}

.login-footer a:hover {
  text-decoration: underline;
  color: #f8df81;
}

.register-link {
  color: #d4af37;
  text-decoration: none;
  font-weight: 500;
  transition: all 0.2s ease;
}

.register-link:hover {
  text-decoration: underline;
  color: #f8df81;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

@keyframes fadeInDown {
  from {
    opacity: 0;
    transform: translateY(-20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes fadeInRight {
  from {
    opacity: 0;
    transform: translateX(-20px);
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

/* 响应式设计 */
@media (max-width: 1100px) {
  .login-content {
    max-width: 1000px;
  }
  
  .login-left {
    padding: 40px;
  }
  
  .feature-text {
    font-size: 1rem;
  }
}

@media (max-width: 992px) {
  .login-content {
    flex-direction: column;
    height: auto;
    min-height: auto;
    max-width: 600px;
  }
  
  .login-left {
    width: 100%;
    padding: 40px;
    clip-path: none;
  }
  
  .login-right {
    width: 100%;
    padding: 40px;
  }
  
  .login-form-container {
    width: 100%;
    padding: 0;
  }
  
  .value-features {
    grid-template-columns: 1fr 1fr;
  }
  
  .brand-quote {
    margin-top: 30px;
  }
}

@media (max-width: 576px) {
  .login-container {
    padding: 15px;
  }
  
  .login-left, .login-right {
    padding: 30px 20px;
  }
  
  .brand-name {
    font-size: 2.2rem;
  }
  
  .value-title {
    font-size: 1.6rem;
    margin-bottom: 25px;
  }
  
  .value-features {
    grid-template-columns: 1fr;
    gap: 15px;
  }
  
  .form-title {
    font-size: 1.8rem;
  }
  
  .form-subtitle {
    font-size: 1rem;
  }
  
  .form-group input {
    padding: 14px 14px 14px 44px;
  }
}
</style>
