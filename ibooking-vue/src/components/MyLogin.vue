
<template>
  <div id="myLogin">
    <p class="login-title">自习室管理系统</p>
    <el-dialog title="登录" width="300px" center :visible.sync="isLogin" :before-close="beforeClose">
      <el-form :model="LoginUser"  status-icon ref="ruleForm" class="demo-ruleForm">
        <el-form-item prop="name">
          <el-input prefix-icon="el-icon-user-solid" placeholder="请输入账号" v-model="LoginUser.name"></el-input>
        </el-form-item>
        <el-form-item prop="pass">
          <el-input
            prefix-icon="el-icon-view"
            type="password"
            placeholder="请输入密码"
            v-model="LoginUser.pass"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button size="medium" type="primary" @click="Login" style="width:100%;">登录</el-button>
          <span>忘记密码？</span>
          <router-link to="/register">
            <span style="float:right;color:blue;cursor: pointer;">注册</span>
          </router-link>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import { login } from '@/api/request.js'

export default {
  name: "MyLogin",
  data() {
    // 用户名的校验方法
    // let validateName = (rule, value, callback) => {
    //   if (!value) {
    //     return callback(new Error("请输入用户名"));
    //   }
    //   // 用户名以字母开头,长度在5-16之间,允许字母数字下划线
    //   const userNameRule = /^[a-zA-Z][a-zA-Z0-9_]{4,15}$/;
    //   if (userNameRule.test(value)) {
    //     this.$refs.ruleForm.validateField("checkPass");
    //     return callback();
    //   } else {
    //     return callback(new Error("字母开头,长度5-16之间,允许字母数字下划线"));
    //   }
    // };
    // 密码的校验方法
    // let validatePass = (rule, value, callback) => {
    //   if (value === "") {
    //     return callback(new Error("请输入密码"));
    //   }
    //   // 密码以字母开头,长度在6-18之间,允许字母数字和下划线
    //   const passwordRule = /^[a-zA-Z]\w{5,17}$/;
    //   if (passwordRule.test(value)) {
    //     this.$refs.ruleForm.validateField("checkPass");
    //     return callback();
    //   } else {
    //     return callback(
    //       new Error("字母开头,长度6-18之间,允许字母数字和下划线")
    //     );
    //   }
    // };
    return {
      isLogin: true,
      LoginUser: {
        name: "",
        pass: ""
      },
      // 用户信息校验规则,validator(校验方法),trigger(触发方式),blur为在组件 Input 失去焦点时触发
      // rules: {
      //   name: [{ validator: validateName, trigger: "blur" }],
      //   pass: [{ validator: validatePass, trigger: "blur" }]
      // }
    };
  },
  computed: {
  },
  methods: {
    beforeClose() {
      return false
    },
    Login() {
      // const form = new FormData()
      // form.append('username_login', this.LoginUser.name)
      // form.append('password_login', this.LoginUser.pass)
      const formData = new FormData();
      formData.append('username', this.LoginUser.name);
      formData.append('password', this.LoginUser.pass);
      const params = {
        username: this.LoginUser.name,
        password: this.LoginUser.pass,
      }
      login(formData).then((res) => {
        // console.log(res)
        if(res.username) {
          localStorage.setItem("username", this.LoginUser.name);
          localStorage.setItem("authorities", res.authorities);
          // localStorage.setItem("role", res.data[2]);
          // this.$router.push('/book')
          this.$message.success('登录成功');
          const role = localStorage.getItem("authorities");
          // console.log(role[0], role[1])
          if (role[0] == "A"){
            // console.log('h')
            this.$router.replace('/roommanage');
          }
          else{
            this.$router.replace('/book');
          }
        } else {
          this.$message.error('用户名或密码错误');
        }
      }) 
    }
  }
};
</script>
<style scoped>
.login-title {
  text-align: center;
  margin-top: 60px;
  color: #000;
  font-size: 30px;
  font-weight: bold;
}
</style>