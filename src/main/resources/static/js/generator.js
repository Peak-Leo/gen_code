$(function () {
    $("#jqGrid").jqGrid({
        url: '/generator/list',
        datatype: "local",
        mtype: 'POST',
        colModel: [
            { label: '表名', name: 'tableName', width: 100, key: true },
            { label: 'Engine', name: 'engine', width: 70},
            { label: '表备注', name: 'tableComment', width: 100 },
            { label: '创建时间', name: 'createTime', width: 100 }
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList : [10,20,50,100,200],
        rownumbers: true,
        rownumWidth: 35,
        autowidth:true,
        multiselect: true,
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
        }
    });
});

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            sourceId: 0,
            tableName: null
        },
        dataSourceList: [],
        groupList: [],
        genForm: {
            groupId: 0
        },
        templateList: []
    },
    methods: {
        query: function () {
            if (vm.q.sourceId <= 0) {
                alert("请选择数据源");
                return;
            }
            $("#jqGrid").jqGrid('setGridParam',{
                datatype: "json",
                postData:{'tableName': vm.q.tableName, 'sourceId': vm.q.sourceId},
                page:1
            }).trigger("reloadGrid");
        },
        templateQuery: function () {
            $.ajax({
                type: "post",
                url: this.serverUrl() + "/template/gen/example-list.json",
                data: JSON.stringify({'groupId': vm.genForm.groupId, 'tableNameList': getSelectedRows()}),
                dataType: "json",
                success: function(data){
                    vm.templateList = data.data;
                },
                error: function (data) {
                    console.log(data);
                }
            });
        },
        preGen: function () {
            var tableNames = getSelectedRows();
            if(tableNames == null){
                return ;
            }
            vm.genForm.groupId = 0;
            vm.templateList = [];
            $('#genForm').modal('show');
        },
        generator: function() {
            var tableNames = getSelectedRows();
            if(tableNames == null){
                return ;
            }
            if (vm.genForm.groupId <= 0) {
                alert("请选择模板组");
                return;
            }
            if (vm.templateList.length === 0) {
                alert("请先配置模板");
                return;
            }
            $('#genForm').modal('hide');
            location.href = "/generator/code?tables=" + JSON.stringify(tableNames) + "&groupId=" + vm.genForm.groupId + "&sourceId=" + vm.q.sourceId;
        },
        getDataSource: function () {
            $.ajax({
                type: "post",
                url: this.serverUrl() + "/datasource/list/all",
                data: JSON.stringify({}),
                dataType: "json",
                success: function(data){
                    vm.dataSourceList = [{id: 0, dataSourceName: '请选择数据源'}];
                    for (var i = 0; i < data.data.length; i++) {
                        vm.dataSourceList.push(data.data[i]);
                    }
                }
            });
        },
        serverUrl: function () {
            return location.protocol + "//" + location.host;
        },
        getGroup: function() {
            $.ajax({
                type: "post",
                url: this.serverUrl() + "/group/list/all",
                data: JSON.stringify({}),
                dataType: "json",
                success: function(data){
                    vm.groupList = [{id: 0, groupName: '请选择模板组'}];
                    for (var i = 0; i < data.data.length; i++) {
                        vm.groupList.push(data.data[i]);
                    }
                }
            });
        },
        groupChange: function () {
            vm.templateQuery();
        },
        sourceChange: function () {
            vm.query();
        }
    }
});

vm.getDataSource();
vm.getGroup();

