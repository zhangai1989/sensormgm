<template>
    <div id="app">
        <router-view/>
    </div>
</template>

<style lang="less">
    @import "assets/css/iconfont.css";
    #app {
        font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        /*text-align: center;*/
        /*color: #2c3e50;*/
    }

    * {
        box-sizing: border-box;
        padding: 0;
        margin: 0;
        /*font-size: 12px;*/
    }

    #nav {
        padding: 30px;
        a {
            font-weight: bold;
            color: #2c3e50;
            &.router-link-exact-active {
                color: #42b983;
            }
        }
    }

    .el-dialog__header {
        background-color: #16d750;
        span {
            color: white;
        }
    }
    .el-dialog__footer {
        background-color: #f2f2f2;
    }

    .el-table__header-wrapper tr:first-child {
        background-color: #16d750;
    }
</style>
