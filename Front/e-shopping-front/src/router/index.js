import AIAssistant from "@/views/AIAssistant.vue";
import Home from "@/views/Home.vue";
import Login from "@/views/Login.vue";
import Register from "@/views/Register.vue";
import Settings from "@/views/Settings.vue";
import { createRouter, createWebHistory } from "vue-router";

// 定义需要登录权限的路由
const authRoutes = ['/home', '/dashboard', '/ai-assistant', '/settings'];

const routes = [
    { path: '/', component: Login },
    { path: '/login', component: Login },
    { path: '/register', component: Register },
    { path: '/home', component: Home },
    { path: '/dashboard', component: Home },
    { path: '/ai-assistant', component: AIAssistant },
    { path: '/settings', component: Settings }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;
