"use strict";(self["webpackChunkvue_project"]=self["webpackChunkvue_project"]||[]).push([[241],{7241:function(e,s,t){t.r(s),t.d(s,{default:function(){return u}});var r=function(){var e=this,s=e.$createElement,t=e._self._c||s;return t("div",{attrs:{id:"register"}},[t("p",{staticClass:"register-title"},[e._v("自习室管理系统")]),t("el-dialog",{attrs:{title:"注册",width:"300px",center:"",visible:e.isRegister,"before-close":e.beforeClose},on:{"update:visible":function(s){e.isRegister=s}}},[t("el-form",{ref:"ruleForm",staticClass:"demo-ruleForm",attrs:{model:e.RegisterUser,"status-icon":""}},[t("el-form-item",[t("el-input",{attrs:{"prefix-icon":"el-icon-user-solid",placeholder:"请输入账号"},model:{value:e.RegisterUser.name,callback:function(s){e.$set(e.RegisterUser,"name",s)},expression:"RegisterUser.name"}})],1),t("el-form-item",[t("el-input",{attrs:{"prefix-icon":"el-icon-view",type:"password",placeholder:"请输入密码"},model:{value:e.RegisterUser.pass,callback:function(s){e.$set(e.RegisterUser,"pass",s)},expression:"RegisterUser.pass"}})],1),t("el-form-item",[t("el-button",{staticStyle:{width:"100%"},attrs:{size:"medium",type:"primary"},on:{click:e.Register}},[e._v("注册")]),t("router-link",{attrs:{to:"/login"}},[t("span",{staticStyle:{color:"blue",cursor:"pointer"}},[e._v("登录")])])],1)],1)],1)],1)},i=[],o=t(6446),a={name:"MyRegister",data(){return{isRegister:!0,RegisterUser:{name:"",pass:"",confirmPass:""}}},watch:{},methods:{beforeClose(){return!1},Register(){const e={username:this.RegisterUser.name,password:this.RegisterUser.pass};(0,o.z2)(e).then((e=>{e&&!0===e.success?(this.$message.success("注册成功,2s后进入登录页面"),setTimeout((()=>{this.$router.push("/login")}),2e3)):this.$message.error("注册失败,"+e.message)}))}}},l=a,n=t(1001),c=(0,n.Z)(l,r,i,!1,null,"156ca21f",null),u=c.exports}}]);