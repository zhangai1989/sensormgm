(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-201ae560"],{2509:function(e,t,a){},"34e9":function(e,t,a){},"43b2":function(e,t,a){"use strict";var l=a("34e9"),i=a.n(l);i.a},4917:function(e,t,a){"use strict";var l=a("cb7c"),i=a("9def"),r=a("0390"),n=a("5f1b");a("214f")("match",1,function(e,t,a,o){return[function(a){var l=e(this),i=void 0==a?void 0:a[t];return void 0!==i?i.call(a,l):new RegExp(a)[t](String(l))},function(e){var t=o(a,e,this);if(t.done)return t.value;var s=l(e),c=String(this);if(!s.global)return n(s,c);var m=s.unicode;s.lastIndex=0;var u,d=[],f=0;while(null!==(u=n(s,c))){var p=String(u[0]);d[f]=p,""===p&&(s.lastIndex=r(c,i(s.lastIndex),m)),f++}return 0===f?null:d}]})},"4a2b":function(e,t,a){"use strict";a.r(t);var l=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("div",{staticClass:"page"},[a("div",{staticClass:"header"},[a("div",{staticStyle:{display:"flex","align-items":"center"}},[a("el-input",{staticStyle:{"margin-right":"15px"},attrs:{placeholder:"请输入用户名","prefix-icon":"el-icon-search",size:"small",clearable:!0},model:{value:e.search,callback:function(t){e.search="string"===typeof t?t.trim():t},expression:"search"}}),a("el-button",{attrs:{type:"primary",size:"small"},on:{click:e.searchList}},[e._v("查询")])],1),a("div",[a("el-button",{attrs:{size:"small"},on:{click:function(t){return e.openDialog(null)}}},[e._v("新增")])],1)]),a("div",{staticClass:"table-box"},[a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],staticStyle:{width:"100%","white-space":"nowrap"},attrs:{data:e.list,border:"",size:"mini"}},[a("el-table-column",{attrs:{fixed:"","show-overflow-tooltip":"",align:"center",prop:"loginName",label:"登录名","min-width":"180"}}),a("el-table-column",{attrs:{align:"center",prop:"cname",label:"真实姓名",width:"140"}}),a("el-table-column",{attrs:{"show-overflow-tooltip":"",align:"center",prop:"areaName",label:"所属区域","min-width":"180"}}),3===e.level?a("el-table-column",{attrs:{"show-overflow-tooltip":"",align:"center",prop:"enterpriseName",label:"所属企业","min-width":"180"}}):e._e(),a("el-table-column",{attrs:{"show-overflow-tooltip":"",align:"center",prop:"mobile",label:"手机号","min-width":"180"}}),a("el-table-column",{attrs:{"show-overflow-tooltip":"",align:"center",prop:"email",label:"邮箱","min-width":"180"}}),a("el-table-column",{attrs:{align:"center",prop:"creatorName",label:"创建人",width:"120"}}),a("el-table-column",{attrs:{align:"center",label:"创建时间",prop:"createTime","min-width":"150"}}),a("el-table-column",{attrs:{align:"center",prop:"modifierName",label:"修改人",width:"120"}}),a("el-table-column",{attrs:{align:"center",label:"修改时间",prop:"modifyTime","min-width":"150"}}),e.allowEdit?a("el-table-column",{attrs:{fixed:"right",label:"操作",align:"center","min-width":"100"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-button",{attrs:{type:"text"},on:{click:function(a){return e.openDialog(t.row)}}},[e._v("修改")]),a("el-button",{attrs:{type:"text"},on:{click:function(a){return e.confirmDelete(t.row.id)}}},[e._v("删除")])]}}],null,!1,523179699)}):e._e()],1)],1),a("div",{staticClass:"pagination"},[a("el-pagination",{attrs:{background:"",layout:"prev, pager, next, jumper","current-page":e.currentPage,pageSize:e.pageSize,total:e.totalNum},on:{"update:currentPage":function(t){e.currentPage=t},"update:current-page":function(t){e.currentPage=t},"current-change":e.changePage}})],1),a("el-dialog",{attrs:{title:e.title,visible:e.editFlag,"show-close":!1,"close-on-click-modal":!1,width:"40%"},on:{"update:visible":function(t){e.editFlag=t}}},[a("el-form",{ref:"editForm",attrs:{inline:!0,model:e.form,rules:e.rules,"label-width":"120px"}},[a("el-form-item",{attrs:{label:"所属区域",prop:"areaId"}},[a("el-select",{staticStyle:{width:"200px"},attrs:{size:"small",placeholder:"请选择",disabled:e.level>2},model:{value:e.form.areaId,callback:function(t){e.$set(e.form,"areaId",t)},expression:"form.areaId"}},e._l(e.areas,function(e){return a("el-option",{key:e.id,attrs:{label:e.name,value:e.id}})}),1)],1),a("el-form-item",{directives:[{name:"show",rawName:"v-show",value:3===e.level,expression:"level === 3"}],attrs:{label:"所属企业",prop:"enterpriseId"}},[a("el-select",{staticStyle:{width:"200px"},attrs:{size:"small",placeholder:"请选择"},model:{value:e.form.enterpriseId,callback:function(t){e.$set(e.form,"enterpriseId",t)},expression:"form.enterpriseId"}},e._l(e.enterprise_list,function(e){return a("el-option",{key:e.id,attrs:{label:e.name,value:e.id}})}),1)],1),a("el-form-item",{attrs:{label:"登录名",prop:"loginName"}},[a("el-input",{attrs:{size:"small",disabled:""!==e.form.id},model:{value:e.form.loginName,callback:function(t){e.$set(e.form,"loginName","string"===typeof t?t.trim():t)},expression:"form.loginName"}})],1),a("el-form-item",{attrs:{label:"真实姓名",prop:"cname"}},[a("el-input",{attrs:{size:"small"},model:{value:e.form.cname,callback:function(t){e.$set(e.form,"cname","string"===typeof t?t.trim():t)},expression:"form.cname"}})],1),a("el-form-item",{attrs:{label:"手机号",prop:"mobile"}},[a("el-input",{attrs:{size:"small",maxLength:"11",oninput:"value=value.replace(/[^\\d]/g,'')"},model:{value:e.form.mobile,callback:function(t){e.$set(e.form,"mobile","string"===typeof t?t.trim():t)},expression:"form.mobile"}})],1),a("el-form-item",{attrs:{label:"邮箱",prop:"email"}},[a("el-input",{attrs:{size:"small"},model:{value:e.form.email,callback:function(t){e.$set(e.form,"email","string"===typeof t?t.trim():t)},expression:"form.email"}})],1)],1),a("span",{attrs:{slot:"footer"},slot:"footer"},[a("el-button",{attrs:{size:"small",disabled:!e.saveAble},on:{click:function(t){e.editFlag=!1}}},[e._v("取 消")]),a("el-button",{attrs:{size:"small",type:"primary",disabled:!e.saveAble},on:{click:function(t){return e.submitForm("editForm")}}},[e._v("保 存")])],1)],1)],1)])},i=[],r=(a("4917"),a("386d"),a("28e6")),n=a("b328"),o=a("bea6"),s=a("491d"),c={components:{ElInput:s["a"],ElFormItem:o["a"],ElForm:n["a"]},data:function(){return{loading:!1,level:1,search:"",list:[],totalNum:0,pageSize:10,currentPage:1,areas:[],enterprise_list:[],form:{id:"",areaId:"",level:"",loginName:"",cname:"",mobile:"",email:"",password:"123456",deleteFlag:0},title:"新增用户",editFlag:!1,allowEdit:!1,rules:{areaId:[{required:!0,message:"所属区域不能为空",trigger:"blur"}],loginName:[{required:!0,message:"登录名不能为空",trigger:"blur"}],cname:[{required:!0,message:"真实姓名不能为空",trigger:"blur"}],mobile:[{required:!0,message:"手机号不能为空",trigger:"blur"},{validator:this.mobileCheck,trigger:"blur"}],email:[{required:!0,message:"邮箱不能为空",trigger:"blur"},{validator:this.emailCheck,trigger:"blur"}]},saveAble:!0}},created:function(){},mounted:function(){this.initData()},methods:{initData:function(){var e=this,t=this;Object(r["e"])().then(function(t){2e3===t.code&&(e.areas=t.result)}),Object(r["a"])({}).then(function(e){2e3===e.code&&(t.enterprise_list=e.result)});var a=parseInt(localStorage.getItem("user.level"));this.level=a,this.allowEdit=a<4;var l={pageNum:1};this.getList(l)},resetForm:function(){this.form.id="",this.form.areaId=this.areas[0].id,this.form.level=parseInt(localStorage.getItem("user.level"))+1,this.form.loginName="",this.form.cname="",this.form.mobile="",this.form.email="",this.form.password="123456"},openDialog:function(e){this.resetForm(),this.$refs["editForm"]&&this.$refs["editForm"].resetFields(),e&&(this.title="修改用户信息",this.form.id=e.id,this.form.areaId=parseInt(e.areaId),this.form.enterpriseId=parseInt(e.enterpriseId),this.form.level=e.level,this.form.loginName=e.loginName,this.form.cname=e.cname,this.form.mobile=e.mobile,this.form.email=e.email,this.form.password=e.password),this.editFlag=!0},getList:function(e){var t=this;e.pageSize=t.pageSize,t.loading=!0,Object(r["q"])(e).then(function(a){2e3===a.code&&(t.currentPage=e.pageNum,t.totalNum=a.result.totalElements,console.log(t.areas),t.list=a.result),t.loading=!1})},changePage:function(e){var t=this,a={pageNum:e};""!==t.search&&(a.enterprise=t.enterprise),"全部"!==t.status&&(a.status=t.status),t.getList(a)},searchList:function(){var e=this;if(e.search.length>10)e.$message.warning("搜索内容请勿太长!");else{var t={pageNum:1};""!==e.search&&(t.name=e.search),e.getList(t)}},submitForm:function(e){var t=this;this.$refs[e].validate(function(e){if(!e)return!1;t.saveAble=!1,t.form.id?Object(r["p"])(t.form).then(function(e){t.saveAble=!0,2e3===e.code&&(t.$message.success("修改成功"),t.editFlag=!1,t.searchList())}):Object(r["d"])(t.form).then(function(e){t.saveAble=!0,2e3===e.code?(t.$message.success("保存成功"),t.editFlag=!1,t.searchList()):t.$message.error(e.message)})})},confirmDelete:function(e){var t=this;this.$confirm("确定要删除？","提示",{confirmButtonText:"确定",cancelButtonText:"取消",customClass:"border-gray",type:"success"}).then(function(){Object(r["h"])({id:e}).then(function(e){2e3===e.code&&(t.$message.success("删除成功"),t.searchList())})}).catch(function(){console.log("取消")})},mobileCheck:function(e,t,a){t&&0!==t.length&&null===t.match(/^1\d{10}$/)?a(new Error("手机号不合法")):a()},emailCheck:function(e,t,a){t&&0!==t.length&&null===t.match(/(^[a-z0-9!#$%&'*+\/=?^_`{|}~.-]+@[a-z0-9]([a-z0-9-]*[a-z0-9])?(\.[a-z0-9]([a-z0-9-]*[a-z0-9])?)*$)/i)?a(new Error("邮箱不合法")):a()}}},m=c,u=(a("43b2"),a("b157"),a("2877")),d=Object(u["a"])(m,l,i,!1,null,"b0194cd6",null);t["default"]=d.exports},b157:function(e,t,a){"use strict";var l=a("2509"),i=a.n(l);i.a}}]);
//# sourceMappingURL=chunk-201ae560.e91029d3.js.map