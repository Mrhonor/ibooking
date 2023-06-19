
<template>
  <div id="register">
    <p class="register-title">自习室管理系统</p>
    <el-dialog title="注册" width="300px" center :visible.sync="isRegister" :before-close="beforeClose">
      <el-form
        :model="RegisterUser"
        status-icon
        ref="ruleForm"
        class="demo-ruleForm"
      >
        <el-form-item >
          <el-input
            prefix-icon="el-icon-user-solid"
            placeholder="请输入账号"
            v-model="RegisterUser.name"
          ></el-input>
        </el-form-item>
        <el-form-item >
          <el-input
            prefix-icon="el-icon-view"
            type="password"
            placeholder="请输入密码"
            v-model="RegisterUser.pass"
          ></el-input>
        </el-form-item>
        <el-form-item >
          <el-input
            prefix-icon="el-icon-view"
            placeholder="请输入邮箱"
            v-model="RegisterUser.email"
          ></el-input>
        </el-form-item>
        <!-- <el-form-item >
          <el-input
            prefix-icon="el-icon-view"
            type="password"
            placeholder="请再次输入密码"
            v-model="RegisterUser.confirmPass"
          ></el-input>
        </el-form-item> -->
        <el-form-item>
          <el-button size="medium" type="primary" @click="Register" style="width:100%;">注册</el-button>
          <router-link to="/login">
            <span style="color:blue;cursor: pointer;">登录</span>
          </router-link>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import { register } from '@/api/request.js'

export default {
  name: "MyRegister",
  data() {
    // 用户名的校验方法
    // let validateName = (rule, value, callback) => {
    //   if (!value) {
    //     return callback(new Error("请输入用户名"));
    //   }
    //   // 用户名以字母开头,长度在5-16之间,允许字母数字下划线
    //   const userNameRule = /^[a-zA-Z][a-zA-Z0-9_]{4,15}$/;
    //   if (userNameRule.test(value)) {
    //     //判断数据库中是否已经存在该用户名
    //     this.$axios
    //       .post("/api/users/findUserName", {
    //         userName: this.RegisterUser.name
    //       })
    //       .then(res => {
    //         // “001”代表用户名不存在，可以注册
    //         if (res.data.code == "001") {
    //           this.$refs.ruleForm.validateField("checkPass");
    //           return callback();
    //         } else {
    //           return callback(new Error(res.data.msg));
    //         }
    //       })
    //       .catch(err => {
    //         return Promise.reject(err);
    //       });
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
    // 确认密码的校验方法
    // let validateConfirmPass = (rule, value, callback) => {
    //   if (value === "") {
    //     return callback(new Error("请输入确认密码"));
    //   }
    //   // 校验是否以密码一致
    //   if (this.RegisterUser.pass != "" && value === this.RegisterUser.pass) {
    //     this.$refs.ruleForm.validateField("checkPass");
    //     return callback();
    //   } else {
    //     return callback(new Error("两次输入的密码不一致"));
    //   }
    // };
    return {
      isRegister: true, // 控制注册组件是否显示
      RegisterUser: {
        name: "",
        pass: "",
        email: "",
        confirmPass: ""
      },
      // 用户信息校验规则,validator(校验方法),trigger(触发方式),blur为在组件 Input 失去焦点时触发
      // rules: {
      //   name: [{ validator: validateName, trigger: "blur" }],
      //   pass: [{ validator: validatePass, trigger: "blur" }],
      //   confirmPass: [{ validator: validateConfirmPass, trigger: "blur" }]
      // }
    };
  },
  watch: {
  
  },
  methods: {
    beforeClose() {
      return false
    },
    Register() {
      // const form = new FormData()
      // form.append('username', this.RegisterUser.name)
      // form.append('password', this.RegisterUser.pass)

      const data = {
        stuNum: this.RegisterUser.name,
        name: "unknown",
        password: this.RegisterUser.pass,
        isAdmin: false,
        email: this.RegisterUser.email,
      }
      register(data).then((res) => {
        console.log(res)
        // if(res) {
        //   // localStorage.setItem("username", res.data[0]);
        //   // localStorage.setItem("password", res.data[1]);
        //   // localStorage.setItem("role", res.data[2]);
        this.$message.success('注册成功,2s后进入登录页面');
        setTimeout(() => {
          this.$router.push('/login')
        },2000)
        // } else {
        //   this.$message.error('注册失败,' + res.message);
        // }
      })
    }
  }
};
</script>
<style scoped>
.register-title {
  text-align: center;
  margin-top: 60px;
  color: #000;
  font-size: 30px;
  font-weight: bold;
}
</style>