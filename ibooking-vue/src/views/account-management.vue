<template>
    <layout>
        <div>
            <el-button type="primary" @click="query">查询</el-button>
            <el-table :data="tableData" border style="width: 100%">
                <el-table-column type="index" label="序号" align="center" width="55px"/>
                <el-table-column prop="stuNum" label="学号" align="center" />
                <el-table-column prop="seatId" label="座位号" align="center"/>
                <el-table-column prop="bookingDate" label="预约日期" align="center"/>
                <el-table-column prop="beginTime" label="开始时间" align="center"/>
                <el-table-column prop="endTime" label="结束时间" align="center"/>
                <el-table-column prop="done" label="是否签到" align="center"/>
            </el-table>
            <el-pagination
                @current-change="handleCurrentChange"
                :current-page="currentPage"
                :page-size="10"
                layout="total, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
        </div>
    </layout>
</template>
<script>
import layout from '@/components/layout-page.vue'

export default ({
    components: {
        layout
    },
    data() {
      return {
        currentPage: 1,
        pagesize: 10,
        total: 10,
        tableData: []
      }
    },
    methods: {
        handleCurrentChange(page) {
            this.currentPage = page
            this.query()
        },
    
        query() {
            const bookingVal = this.$route.params.bookingInfo
            this.tableData = bookingVal.map(item => {
                item.done = item.done === true ? '是' : '否'
                return item
            })
            this.total = bookingVal.length
        },
    },
    mounted() {
        this.query()
    },
})
</script>
<style scoped>
.el-table {
    margin-top: 15px;
}
.upload-demo {
    display: inline-block;
    margin-top: 15px;
}
.el-pagination {
    margin-top: 15px;
    float: right;
}
.el-select {
    margin-left: 15px;
    margin-right: 15px;
}
</style>
