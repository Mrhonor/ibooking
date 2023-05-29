<template>
    <layout>
        <div>
            <el-button type="primary" @click="query">查询</el-button>
            <el-table :data="tableData" border style="width: 100%">
                <el-table-column type="index" label="序号" align="center" width="55px" />
                <el-table-column prop="stuNum" label="学号" align="center" />
                <el-table-column prop="password" label="密码" align="center"/>
                <el-table-column prop="salt" label="座位" align="center"/>
                <el-table-column prop="registerTime" label="注册时间" align="center"/>
                <el-table-column
                    fixed="right"
                    label="操作"
                    align="center"
                    width="100">
                    <template slot-scope="scope">
                        <el-button @click="done(scope.row)" type="text" size="small">签到</el-button>
                        <el-button @click="booking(scope.row)" type="text" size="small">预约</el-button>
                        <el-button @click="check(scope.row)" type="text" size="small">查看</el-button>
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
            <el-dialog title="预约" :visible.sync="Visible" width="500px">
                <el-form :model="form">
                    <el-form-item label="预约时间" >
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
                  <el-button @click="Visible = false">取 消</el-button>
                </span>
            </el-dialog>
        </div>
    </layout>
</template>
<script>
import layout from '@/components/layout-page.vue'
import { getStudent, booking, bookingCheck} from '@/api/request'

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
        bookingInfo: [],
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
            getStudent().then((res) => {
                if(res) {
                  this.tableData = res
                  this.total = res.length
                } 
            })
        },
        done(val) {
            const params = val.stuNum
            bookingCheck(params).then((res) => {
                if(res) {
                    this.$message.success('签到成功')
                    this.bookingInfo = res['预约信息']
                    localStorage.setItem('bookingInfo', JSON.stringify(this.bookingInfo) )

                } else {
                    this.$message.success('签到失败')
                }
            })
        },
        booking(val) {
            this.bookingVal = val
            this.Visible = true
        },
        check() {
            if(this.bookingInfo.length === 0) {
                this.$message.info('请先进行签到或者预约,才能查看预约信息')
                return
            }
            this.$router.push({ name: 'account', params: {bookingInfo: this.bookingInfo} })
        },
        sure() {
            if( !this.form.time) {
                this.$message.info('请选择预约开始时间和结束时间')
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
                    this.bookingInfo = res['预约信息']
                    localStorage.setItem('bookingInfo', JSON.stringify(this.bookingInfo))
                    this.$message.success('预约成功')
                } else {
                    this.$message.success('预约失败')
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
