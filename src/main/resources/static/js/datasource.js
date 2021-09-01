$(function () {
    $("#jqGrid").jqGrid({
        url: '/datasource/list',
        datatype: "json",
        mtype: 'POST',
        colModel: [
            { label: 'id', name: 'id', width: 50, key: true, hidden: true},
            {
                label: '操作', name: 'operate', width: 60,
                formatter: function (cellvalue, options, rowObject) {
                    return '<a href="javascript:void(0);" data-toggle="tooltip" data-placement="bottom" title="编辑" class="btn btn-default" onclick="vm.get(' + options.rowId +')"><span class="glyphicon glyphicon-pencil"></span></a>&nbsp;&nbsp;<a href="javascript:void(0);" data-toggle="tooltip" data-placement="bottom" title="删除" class="btn btn-default" onclick="vm.delete(' + options.rowId +')"><span class="glyphicon glyphicon-remove"></span></a>';
                }
            },
            { label: '数据源名称', name: 'dataSourceName', width: 50},
            { label: '驱动名称', name: 'driverClassName', width: 100},
            { label: '数据库连接', name: 'url', width: 150 },
            { label: '数据库', name: 'dbName', width: 50 },
            { label: '用户名', name: 'userName', width: 50 },
            { label: '密码', name: 'userPassword', width: 50 }
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList : [10,20,50,100,200],
        rownumbers: true,
        rownumWidth: 35,
        autowidth:true,
        multiselect: false,
        pager: "#jqGridPager",
        jsonReader : {
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
        },
        prmNames : {
            page:"page",
            rows:"limit",
            order: "order"
        },
        gridComplete:function(){
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        },
        loadComplete:function(data) {
            if ("false" === data.result) {
                alert(data.message);
            }
        },
        serializeGridData: function (postData) {
            return JSON.stringify(postData);
        },
    });

    $("[data-toggle='tooltip']").tooltip();
});

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            name: null
        },
        addForm: {
            dataSourceName:'',
            driverClassName:'com.mysql.jdbc.Driver',
            url: 'jdbc:mysql://localhost:3306',
            dbName: '',
            userName: '',
            userPassword: ''
        },
        editForm: {
            dataSourceName:'',
            driverClassName:'',
            url: '',
            dbName: '',
            userName: '',
            userPassword: ''
        }
    },
    methods: {
        query: function () {
            $("#jqGrid").jqGrid('setGridParam',{
                datatype: "json",
                postData:{'dataSourceName': vm.q.name},
                page:1
            }).trigger("reloadGrid");
        },
        serverUrl: function () {
            return location.protocol + "//" + location.host;
        },
        get: function(id) {
            $.ajax({
                type: "post",
                url: this.serverUrl() + "/datasource/get",
                data: JSON.stringify({id: id}),
                dataType: "json",
                success: function(data){
                    vm.editForm = data.data;
                    $('#editForm').modal('show');
                }
            });
        },
        edit: function() {
            $.ajax({
                type: "post",
                url: this.serverUrl() + "/datasource/update",
                data: JSON.stringify(vm.editForm),
                dataType: "json",
                success: function(data){
                    if ("false" === data.result) {
                        alert(data.message);
                    } else {
                        $('#editForm').modal('hide');
                        vm.query();
                    }
                }
            });
        },
        delete: function (id) {
            confirm("确认删除？", function(){
                $.ajax({
                    type: "post",
                    url: vm.serverUrl() + "/datasource/delete",
                    data: JSON.stringify({id: id}),
                    dataType: "json",
                    success: function(data){
                        if ("false" === data.result) {
                            alert(data.message);
                        } else {
                            vm.query();
                        }
                    }
                });
            });
        },
        insert: function () {
            $.ajax({
                type: "post",
                url: this.serverUrl() + "/datasource/insert",
                data: JSON.stringify(vm.addForm),
                dataType: "json",
                success: function(data){
                    $('#addForm').modal('hide');
                    vm.query();
                    vm.addForm = {
                        dataSourceName: '',
                        driverClassName: 'com.mysql.jdbc.Driver',
                        url: 'jdbc:mysql://localhost:3306',
                        dbName: '',
                        userName: '',
                        userPassword: ''
                    };
                }
            });
        },
        connectTestByInsert: function () {
            $.ajax({
                type: "post",
                url: this.serverUrl() + "/datasource/connect/test",
                data: JSON.stringify(vm.addForm),
                dataType: "json",
                success: function(data){
                    if ("false" === data.result) {
                        alert(data.message);
                    } else {
                        alert(data.message);
                    }
                }
            });
        },
        connectTestByEdit: function () {
            $.ajax({
                type: "post",
                url: this.serverUrl() + "/datasource/connect/test",
                data: JSON.stringify(vm.editForm),
                dataType: "json",
                success: function(data){
                    // if ("false" === data.result) {
                        alert(data.message);
                    // } else {
                    //     alert(data.data);
                    // }
                }
            });
        }
    }
});

