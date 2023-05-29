<template>
  <el-menu
    :default-active="activeIndex"
    class="el-menu-vertical-demo"
    background-color="#545c64"
    text-color="#fff"
    active-text-color="#ffd04b"
    @select="handleSelect"
  >
    <!-- <el-menu-item index="1" @click="$router.push('/task')">
      <i class="el-icon-s-order"></i>
      <span>预约管理</span>
    </el-menu-item> -->
    <el-menu-item index="1" @click="$router.push('/roommanage')">
      <i class="el-icon-s-order"></i>
      <span>自习室管理</span>
    </el-menu-item>
    <el-menu-item index="2" @click="$router.push('/account')">
      <i class="el-icon-user"></i>
      <span>预约信息</span>
    </el-menu-item>
    <el-menu-item index="3" @click="$router.push('/unbooking')">
      <i class="el-icon-user"></i>
      <span>退预约管理</span>
    </el-menu-item>
  </el-menu>
</template>
<script>
export default {
  components: {},
  data() {
    return {
      activeIndex: "",
    };
  },
  methods: {
    handleSelect(key) {
      this.activeIndex = key;
    },
  },
  mounted() {
  },
};
</script>
<style scoped>
.el-menu {
  height: 100%;
}

a {
  text-decoration: none;
  color: #fff;
}
label {
  cursor: pointer;
}
</style>