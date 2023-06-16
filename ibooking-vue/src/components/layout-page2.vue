<template>
    <div class="layout2" >
        <layout>
            <el-header>
                <el-steps :active="active" finish-status="success">
                    <el-step title="选择自习室"></el-step>
                    <el-step title="选择座位"></el-step>
                    <el-step title="预约"></el-step>
                </el-steps>
            </el-header>
        </layout>
    </div>
</template>
<script>
import layout from '@/components/layout-page.vue'

export default ({
    components: {
        layout,
    },
    data() {
      return {
        active: 0,
      }
    },
    methods: {
        next() {
            if (this.active++ > 2) this.active = 0;
        }
    },
    mounted() {
    },
})
</script>
<style scoped>
.layout2,
.el-container {
  height: 100%;
}
.el-pagination {
    margin-top: 15px;
    float: right;
}
</style>