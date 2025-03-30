<template>
  <div class="register-container">
    <div class="background-shapes">
      <div class="shape shape-1"></div>
      <div class="shape shape-2"></div>
      <div class="shape shape-3"></div>
      <div class="shape shape-4"></div>
    </div>
    <div class="register-content">
      <div class="register-left">
        <div class="brand-container">
          <h1 class="brand-name">ES-Shopping</h1>
          <p class="brand-tagline">卖家管理平台</p>
        </div>
        <div class="value-props">
          <h2 class="value-title">加入商家生态系统</h2>
          <div class="value-features">
            <div class="feature">
              <div class="feature-icon"><span class="material-icon">store</span></div>
              <div class="feature-text">完整店铺管理</div>
            </div>
            <div class="feature">
              <div class="feature-icon"><span class="material-icon">insights</span></div>
              <div class="feature-text">销售数据可视化</div>
            </div>
            <div class="feature">
              <div class="feature-icon"><span class="material-icon">support_agent</span></div>
              <div class="feature-text">客户服务工具</div>
            </div>
            <div class="feature">
              <div class="feature-icon"><span class="material-icon">auto_awesome</span></div>
              <div class="feature-text">智能营销策略</div>
            </div>
          </div>
        </div>
        <div class="brand-quote">
          <p>"成为数字化商家，开启增长新时代"</p>
        </div>
      </div>
      <div class="register-right">
        <div class="register-form-container">
          <div class="form-header">
            <h2 class="form-title">商家注册</h2>
            <p class="form-subtitle">创建您的卖家账户，开始您的电商之旅</p>
          </div>
          <form class="register-form" @submit.prevent="handleRegister">
            <!-- 用户名 -->
            <div class="form-group" :class="{ 'focused': activeField === 'username', 'filled': formData.username, 'error': errors.username }">
              <label for="username">用户名</label>
              <div class="input-container">
                <span class="input-icon"><span class="material-icon">person</span></span>
                <input 
                  type="text" 
                  id="username"
                  v-model="formData.username"
                  @focus="setActiveField('username')"
                  @blur="validateUsername"
                  autocomplete="username"
                >
              </div>
              <div class="error-message" v-if="errors.username">{{ errors.username }}</div>
            </div>
            
            <!-- 邮箱 -->
            <div class="form-group" :class="{ 'focused': activeField === 'email', 'filled': formData.email, 'error': errors.email }">
              <label for="email">电子邮箱</label>
              <div class="input-container">
                <span class="input-icon"><span class="material-icon">email</span></span>
                <input 
                  type="email" 
                  id="email"
                  v-model="formData.email"
                  @focus="setActiveField('email')"
                  @blur="validateEmail"
                  autocomplete="email"
                >
              </div>
              <div class="error-message" v-if="errors.email">{{ errors.email }}</div>
            </div>
            
            <!-- 手机号 -->
            <div class="form-group" :class="{ 'focused': activeField === 'phone', 'filled': formData.phone, 'error': errors.phone }">
              <label for="phone">手机号码</label>
              <div class="input-container">
                <span class="input-icon"><span class="material-icon">phone</span></span>
                <input 
                  type="tel" 
                  id="phone"
                  v-model="formData.phone"
                  @focus="setActiveField('phone')"
                  @blur="validatePhone"
                  autocomplete="tel"
                >
              </div>
              <div class="error-message" v-if="errors.phone">{{ errors.phone }}</div>
            </div>

            <!-- 密码 -->
            <div class="form-group" :class="{ 'focused': activeField === 'password', 'filled': formData.password, 'error': errors.password }">
              <label for="password">密码</label>
              <div class="input-container">
                <span class="input-icon"><span class="material-icon">lock</span></span>
                <input 
                  :type="showPassword ? 'text' : 'password'" 
                  id="password"
                  v-model="formData.password"
                  @focus="setActiveField('password')"
                  @blur="validatePassword"
                  autocomplete="new-password"
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
              <div class="error-message" v-if="errors.password">{{ errors.password }}</div>
              <div class="password-strength" v-if="formData.password">
                <div class="strength-text">密码强度: <span :class="passwordStrengthClass">{{ passwordStrengthLabel }}</span></div>
                <div class="strength-bar">
                  <div class="strength-indicator" :style="{ width: passwordStrengthPercentage + '%', backgroundColor: passwordStrengthColor }"></div>
                </div>
              </div>
            </div>
            
            <!-- 确认密码 -->
            <div class="form-group" :class="{ 'focused': activeField === 'confirmPassword', 'filled': formData.confirmPassword, 'error': errors.confirmPassword }">
              <label for="confirmPassword">确认密码</label>
              <div class="input-container">
                <span class="input-icon"><span class="material-icon">lock_clock</span></span>
                <input 
                  :type="showConfirmPassword ? 'text' : 'password'" 
                  id="confirmPassword"
                  v-model="formData.confirmPassword"
                  @focus="setActiveField('confirmPassword')"
                  @blur="validateConfirmPassword"
                  autocomplete="new-password"
                >
                <button 
                  type="button" 
                  class="toggle-password"
                  @click="toggleConfirmPasswordVisibility"
                  aria-label="Toggle confirm password visibility"
                >
                  <span class="material-icon">{{ showConfirmPassword ? 'visibility_off' : 'visibility' }}</span>
                </button>
              </div>
              <div class="error-message" v-if="errors.confirmPassword">{{ errors.confirmPassword }}</div>
            </div>

            <div class="form-options">
              <label class="accept-terms">
                <div class="custom-checkbox">
                  <input type="checkbox" v-model="formData.agreeTerms" id="terms" @change="validateField('agreeTerms')">
                  <span class="checkmark"></span>
                </div>
                <span class="checkbox-label">我同意 <a href="#" class="terms-link">服务条款</a> 和 <a href="#" class="terms-link">隐私政策</a></span>
              </label>
            </div>
            <div class="error-message terms-error" v-if="errors.agreeTerms">{{ errors.agreeTerms }}</div>
            
            <button type="submit" class="register-button" :disabled="isLoading || !isValidForm">
              <span v-if="isLoading" class="loader"></span>
              <span v-else>注册账号</span>
            </button>

            <!-- 错误消息 -->
            <div v-if="errorMessage" class="error-message global-error">
              <span class="material-icon">error_outline</span>
              <span>{{ errorMessage }}</span>
            </div>
            
            <!-- 成功消息 -->
            <div v-if="successMessage" class="success-message">
              <span class="material-icon">check_circle_outline</span>
              <span>{{ successMessage }}</span>
            </div>
          </form>
          <div class="register-footer">
            <p>已有账户? <router-link to="/login" class="login-link">登录</router-link></p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'RegisterView',
  data() {
    return {
      formData: {
        username: '',
        email: '',
        phone: '',
        password: '',
        confirmPassword: ''
      },
      errors: {
        username: '',
        email: '',
        phone: '',
        password: '',
        confirmPassword: ''
      },
      passwordStrength: 0,
      activeField: null,
      showPassword: false,
      showConfirmPassword: false,
      isLoading: false,
      errorMessage: '',
      successMessage: ''
    }
  },
  mounted() {
    document.title = "ES-Shopping 卖家管理平台 - 注册";
    this.addGlassMorphismEffect();
    window.addEventListener('mousemove', this.handleMouseMove);
  },
  beforeUnmount() {
    window.removeEventListener('mousemove', this.handleMouseMove);
  },
  computed: {
    isValidForm() {
      return (
        this.formData.username && 
        this.formData.email && 
        this.formData.phone && 
        this.formData.password && 
        this.formData.confirmPassword &&
        !this.errors.username &&
        !this.errors.email &&
        !this.errors.phone &&
        !this.errors.password &&
        !this.errors.confirmPassword
      );
    },
    passwordStrengthLabel() {
      if (this.passwordStrength === 0) return '弱';
      if (this.passwordStrength === 1) return '中';
      if (this.passwordStrength === 2) return '强';
      return '非常强';
    },
    passwordStrengthClass() {
      return {
        'weak': this.passwordStrength === 0,
        'medium': this.passwordStrength === 1,
        'strong': this.passwordStrength === 2,
        'very-strong': this.passwordStrength === 3
      };
    },
    passwordStrengthPercentage() {
      if (this.passwordStrength === 0) return 33;
      if (this.passwordStrength === 1) return 66;
      if (this.passwordStrength === 2) return 100;
      return 0;
    },
    passwordStrengthColor() {
      if (this.passwordStrength === 0) return '#ff4d4f';
      if (this.passwordStrength === 1) return '#faad14';
      if (this.passwordStrength === 2) return '#52c41a';
      return '';
    }
  },
  methods: {
    handleRegister() {
      // 首先进行表单验证
      this.validateAll();
      
      if (!this.isValidForm) {
        this.errorMessage = '请正确填写所有必填项';
        return;
      }
      
      if (this.formData.password !== this.formData.confirmPassword) {
        this.errors.confirmPassword = '两次输入的密码不一致';
        return;
      }
      
      this.isLoading = true;
      this.errorMessage = '';
      this.successMessage = '';
      
      // 发送注册请求
      axios.post('/admin/user/register', {
        username: this.formData.username,
        email: this.formData.email,
        phone: this.formData.phone,
        password: this.formData.password
      })
      .then(response => {
        this.isLoading = false;
        const data = response.data;
        
        if (data.code === 200) {
          // 注册成功
          this.successMessage = '注册成功！即将跳转到登录页面...';
          
          // 3秒后跳转到登录页
          setTimeout(() => {
            this.$router.push('/login');
          }, 3000);
        } else {
          // 注册失败
          this.errorMessage = data.msg || '注册失败，请稍后重试';
        }
      })
      .catch(error => {
        this.isLoading = false;
        
        if (error.response) {
          // 服务器返回了错误状态码
          this.errorMessage = error.response.data.msg || `服务器错误 (${error.response.status})`;
        } else if (error.request) {
          // 请求发出但没有收到响应
          this.errorMessage = '无法连接到服务器，请检查网络连接';
        } else {
          // 请求设置时出错
          this.errorMessage = '请求错误，请稍后重试';
        }
        
        console.error('注册请求错误:', error);
      });
    },
    setActiveField(field) {
      this.activeField = field;
    },
    validateUsername() {
      if (!this.formData.username) {
        this.errors.username = '用户名不能为空';
        return;
      }
      
      if (this.formData.username.length < 3) {
        this.errors.username = '用户名至少需要3个字符';
        return;
      }
      
      if (!/^[a-zA-Z0-9_]+$/.test(this.formData.username)) {
        this.errors.username = '用户名只能包含字母、数字和下划线';
        return;
      }
      
      this.errors.username = '';
    },
    validateEmail() {
      if (!this.formData.email) {
        this.errors.email = '邮箱不能为空';
        return;
      }
      
      if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(this.formData.email)) {
        this.errors.email = '请输入有效的邮箱地址';
        return;
      }
      
      this.errors.email = '';
    },
    validatePhone() {
      if (!this.formData.phone) {
        this.errors.phone = '手机号不能为空';
        return;
      }
      
      if (!/^1[3-9]\d{9}$/.test(this.formData.phone)) {
        this.errors.phone = '请输入有效的手机号码';
        return;
      }
      
      this.errors.phone = '';
    },
    validatePassword() {
      if (!this.formData.password) {
        this.errors.password = '密码不能为空';
        this.passwordStrength = 0;
        return;
      }
      
      if (this.formData.password.length < 6) {
        this.errors.password = '密码至少需要6个字符';
        this.passwordStrength = 0;
        return;
      }
      
      // 计算密码强度
      let strength = 0;
      
      // 检查密码长度
      if (this.formData.password.length >= 8) {
        strength += 1;
      }
      
      // 检查是否包含数字和字母
      if (/[a-zA-Z]/.test(this.formData.password) && /[0-9]/.test(this.formData.password)) {
        strength += 1;
      }
      
      // 检查是否包含特殊字符
      if (/[^a-zA-Z0-9]/.test(this.formData.password)) {
        strength += 1;
      }
      
      this.passwordStrength = strength;
      this.errors.password = '';
    },
    validateConfirmPassword() {
      if (!this.formData.confirmPassword) {
        this.errors.confirmPassword = '请确认密码';
        return;
      }
      
      if (this.formData.password !== this.formData.confirmPassword) {
        this.errors.confirmPassword = '两次输入的密码不一致';
        return;
      }
      
      this.errors.confirmPassword = '';
    },
    validateAll() {
      this.validateUsername();
      this.validateEmail();
      this.validatePhone();
      this.validatePassword();
      this.validateConfirmPassword();
    },
    togglePasswordVisibility() {
      this.showPassword = !this.showPassword;
    },
    toggleConfirmPasswordVisibility() {
      this.showConfirmPassword = !this.showConfirmPassword;
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

.register-container {
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

.register-content {
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

.register-left {
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

.register-left::before {
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

.register-left::after {
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

.register-right {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px;
  backdrop-filter: blur(16px);
  -webkit-backdrop-filter: blur(16px);
  background: rgba(18, 18, 18, 0.9);
  overflow-y: auto;
}

.register-form-container {
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

.register-form {
  display: flex;
  flex-direction: column;
  gap: 22px;
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

.form-group.error input {
  border-color: #ff4d4f;
  box-shadow: 0 0 0 4px rgba(255, 77, 79, 0.1);
}

.error-message {
  display: flex;
  align-items: center;
  padding: 12px;
  background: rgba(220, 53, 69, 0.1);
  color: #dc3545;
  border-radius: 10px;
  margin-top: 5px;
  font-size: 0.9rem;
  border-left: 3px solid #dc3545;
}

.error-message.global-error {
  margin: 15px 0;
}

.error-message .material-icon {
  margin-right: 10px;
  font-size: 20px;
}

.success-message {
  display: flex;
  align-items: center;
  padding: 12px;
  background: rgba(40, 167, 69, 0.1);
  color: #28a745;
  border-radius: 10px;
  margin: 15px 0;
  font-size: 0.9rem;
  border-left: 3px solid #28a745;
}

.success-message .material-icon {
  margin-right: 10px;
  font-size: 20px;
}

.terms-error {
  margin-top: -12px;
  margin-bottom: 8px;
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

.password-strength {
  margin-top: 10px;
  font-size: 0.85rem;
}

.strength-text {
  display: flex;
  align-items: center;
  margin-bottom: 6px;
  color: #9e9e9e;
}

.weak { color: #ff4d4f; }
.medium { color: #faad14; }
.strong { color: #52c41a; }

.strength-bar {
  height: 4px;
  width: 100%;
  background-color: #333333;
  border-radius: 2px;
  overflow: hidden;
}

.strength-indicator {
  height: 100%;
  border-radius: 2px;
  transition: width 0.3s ease, background-color 0.3s ease;
}

.form-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 0.95rem;
  margin-top: 8px;
}

.accept-terms {
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

.terms-link {
  color: #d4af37;
  text-decoration: none;
  font-weight: 500;
  transition: all 0.2s ease;
}

.terms-link:hover {
  color: #f8df81;
  text-decoration: underline;
}

.register-button {
  margin-top: 16px;
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

.register-button::before {
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

.register-button:hover {
  transform: translateY(-3px);
  box-shadow: 0 8px 25px rgba(212, 175, 55, 0.4);
}

.register-button:hover::before {
  left: 100%;
}

.register-button:disabled {
  background: linear-gradient(135deg, #4a4a4a 0%, #2a2a2a 100%);
  color: #888888;
  cursor: not-allowed;
  transform: none;
  box-shadow: none;
}

.register-button:active:not(:disabled) {
  transform: translateY(0);
  box-shadow: 0 4px 12px rgba(212, 175, 55, 0.25);
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

.register-footer {
  margin-top: 30px;
  text-align: center;
  color: #9e9e9e;
  font-size: 0.95rem;
}

.login-link {
  color: #d4af37;
  text-decoration: none;
  font-weight: 500;
}

.login-link:hover {
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
  .register-content {
    max-width: 1000px;
  }
  
  .register-left {
    padding: 40px;
  }
  
  .feature-text {
    font-size: 1rem;
  }
}

@media (max-width: 992px) {
  .register-content {
    flex-direction: column;
    height: auto;
    min-height: auto;
    max-width: 600px;
  }
  
  .register-left {
    width: 100%;
    padding: 40px;
    clip-path: none;
  }
  
  .register-right {
    width: 100%;
    padding: 40px;
  }
  
  .register-form-container {
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
  .register-container {
    padding: 15px;
  }
  
  .register-left, .register-right {
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