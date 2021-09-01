$(function () {
    $("#jqGrid").jqGrid({
        url: '/group/list',
        datatype: "json",
        mtype: 'POST',
        colModel: [
            { label: 'id', name: 'id', width: 50, key: true, hidden: true},
            {
                label: '操作', name: 'operate', width: 40,
                formatter: function (cellvalue, options, rowObject) {
                    return '<a href="javascript:void(0);" data-toggle="tooltip" data-placement="bottom" title="编辑" class="btn btn-default" onclick="vm.get(' + options.rowId +')"><span class="glyphicon glyphicon-pencil"></span></a>&nbsp;&nbsp;<a href="javascript:void(0);" data-toggle="tooltip" data-placement="bottom" title="删除" class="btn btn-default" onclick="vm.delete(' + options.rowId +')"><span class="glyphicon glyphicon-remove"></span></a>';
                }
            },
            { label: '模板组名称', name: 'groupName', width: 50},
            { label: '模块名', name: 'moduleName', width: 50},
            { label: '作者', name: 'author', width: 50},
            { label: '表前缀', name: 'tablePrefix', width: 20 },
            { label: '主包名', name: 'mainPackage', width: 100 }
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
            groupName:'',
            moduleName:'',
            author:'',
            tablePrefix: '',
            mainPackage: ''
        },
        editForm: {
            id: '',
            groupName:'',
            moduleName:'',
            author:'',
            tablePrefix: '',
            mainPackage: ''
        }
    },
    methods: {
        query: function () {
            $("#jqGrid").jqGrid('setGridParam',{
                datatype: "json",
                postData:{'groupName': vm.q.name},
                page:1
            }).trigger("reloadGrid");
        },
        serverUrl: function () {
            return location.protocol + "//" + location.host;
        },
        get: function(id) {
            $.ajax({
                type: "post",
                url: this.serverUrl() + "/group/get",
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
                url: this.serverUrl() + "/group/update",
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
                    url: vm.serverUrl() + "/group/delete",
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
                url: this.serverUrl() + "/group/insert",
                data: JSON.stringify(vm.addForm),
                dataType: "json",
                success: function(data){
                    $('#addForm').modal('hide');
                    vm.query();
                    vm.addForm = {
                        groupName:'',
                        moduleName:'',
                        author:'',
                        tablePrefix: '',
                        mainPackage: ''
                    };
                }
            });
        }
    }
});

