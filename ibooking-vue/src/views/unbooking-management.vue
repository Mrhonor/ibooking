<template>
    <layout>
        <div>
            <el-button type="primary" @click="query">查询</el-button>
            <el-table :data="tableData" border style="width: 100%">
                <el-table-column type="index" label="序号" align="center" width="55px" />
                <el-table-column prop="stuNum" label="学号" align="center" />
                <el-table-column prop="seatId" label="座位号" align="center"/>
                <el-table-column prop="bookingDate" label="预约日期" align="center"/>
                <el-table-column prop="beginTime" label="开始时间" align="center"/>
                <el-table-column prop="endTime" label="结束时间" align="center"/>
                <el-table-column
                    fixed="right"
                    label="操作"
                    align="center"
                    width="100">
                    <template slot-scope="scope">
                        <el-button @click="unbooking(scope.row)" type="text" size="small">退预约</el-button>
                        <el-button @click="booking(scope.row)" type="text" size="small">续约</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination
                @current-change="handleCurrentChange"
                :current-page="currentPage"
                :page-size="10"
                layout="total, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
            <el-dialog title="续约" :visible.sync="Visible" width="500px">
                <el-form :model="form">
                    <el-form-item label="续约时间" >
                      <el-date-picker
                        v-model="form.time"
                        type="daterange"
                        range-separator="至"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期"
                        value-format="yyyy-MM-dd HH:mm">
                        </el-date-picker>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                  <el-button type="primary" @click="sure">确 定</el-button>
                  <el-button @click="dialogVisible = false">取 消</el-button>
                </span>
            </el-dialog>
        </div>
    </layout>
</template>
<script>
import layout from '@/components/layout-page.vue'
import {  booking ,bookingDel} from '@/api/request'

export default ({
    components: {
        layout,
    },
    data() {
      return {
        currentPage: 1,
        pagesize: 10,
        total: 10,
        tableData: [],
        Visible: false,
        bookingVal: '',
        form: {
            time: ''
        }
      }
    },
    methods: {
        handleCurrentChange(page) {
            this.currentPage = page
            this.query()
        },
        query() {
            const bookingInfo = localStorage.getItem('bookingInfo')
            this.tableData = JSON.parse(bookingInfo)
            this.total = this.tableData.length
        },
        booking(val) {
            this.bookingVal = val
            this.Visible = true
        },
        unbooking(val) {
            this.$confirm('确定要退预约吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                bookingDel(val.id).then((res) => {
                    if(res) {
                        this.query()
                        this.$message.success('退预约成功')
                    } else {
                        this.$message.success('退预约失败')
                    }
                })
            }).catch(() => {
                this.$message.info('已取消')
            })
        },
        sure() {
            if( !this.form.time) {
                this.$message.info('请选择续约开始时间和结束时间')
                return
            }
            let currentDate = new Date();
            let strDate = currentDate.getFullYear()+"-"+(currentDate.getMonth()+1)+"-"+currentDate.getDate();
            const params = {
                beginTime: this.form.time[0],
                endTime: this.form.time[1],
                bookingDate: strDate,
                done: true,
                id: this.bookingVal.id,
                seatId: 0,
                stuNum: this.bookingVal.stuNum
                
            }
             booking(params).then((res) => {
                if(res && res.code == 0) {
                    this.Visible = false
                    this.form = {
                        time: ''
                    }
                    this.$message.success('续约成功')
                } else {
                    this.$message.success('续约失败')
                }
            })
        }
     

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
.el-pagination {
    margin-top: 15px;
    float: right;
}
</style>
