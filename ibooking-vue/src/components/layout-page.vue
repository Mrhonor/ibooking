<template>
  <div class="layout">
    <el-container>
      <el-header>
        <div style="float:left;font-weight: bold;">
        <img src="../assets/logo.png" alt="" style="width:40px;height:40px;transform: translateY(10px);">
          自习室管理系统
        </div>
        <div style="float:right">
          <ul>  
            <li>
              欢迎
              <el-popover placement="top" width="180" v-model="visible">
                <p>确定退出登录吗？</p>
                <div style="text-align: right; margin: 10px 0 0">
                  <el-button size="mini" type="text" @click="visible = false">取消</el-button>
                  <el-button type="primary" size="mini" @click="logout">确定</el-button>
                </div>
                <el-button type="text" slot="reference">{{ username }}</el-button>
              </el-popover>
            </li>
          </ul>
        </div>
      </el-header>
      <el-container>
        <el-aside width="200px">
          <left-menu v-if="authorities[0] === 'ADMIN'"></left-menu>
          <left-menu-student v-else></left-menu-student>
        </el-aside>
        <el-container>
          <el-main>
            <slot></slot>
          </el-main>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>
<script>
import leftMenuStudent from './left-menu-student.vue'
import leftMenu from './left-menu.vue'

export default ({
  components: {
    leftMenu,
    leftMenuStudent,
  },
  data() {
    return {
      username: '',
      visible: false,
      authorities: [],
    }
  },
  methods: {
    login() {
      this.isLogin = true
    },
    register() {
      this.isRegister = true
    },
    logout() {
      localStorage.clear()
      this.$router.push('/login')
    }
  },
  mounted() {
    this.username  = window.localStorage.getItem('username')
    this.authorities = window.localStorage.getItem('authorities').split(',')
  }
  
})
</script>


<style scoped>
.layout,
.el-container {
  height: 100%;
}
.el-button--text{
  color: #000;
}

.el-header,
.el-footer {
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #D3DCE6;
  color: #333;
  text-align: center;
  line-height: 200px;
}

body>.el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}
</style>