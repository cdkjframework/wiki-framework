<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Code Auto Generator By cdk Framework</title>
    <!-- import CSS -->
    <link rel="stylesheet" href="/static/index.css">
    <link rel="stylesheet" href="https://unpkg.com/element-ui@2.7.2/lib/theme-chalk/index.css">
    <style type="text/css">

        .el-header, .el-footer {
            background-color: #B3C0D1;
            color: #333;
            text-align: center;
            line-height: 60px;
        }

        .el-aside {
            background-color: #D3DCE6;
            color: #333;
            text-align: center;
            line-height: 200px;
        }

        .el-main {
            background-color: #E9EEF3;
            color: #333;
            text-align: left;
            position: absolute;
            top: 60px;
            left: 0;
            bottom: 0;
            right: 0;
        }

        .el-container,
        .is-vertical {
            position: absolute;
            top: 0;
            left: 0;
            bottom: 0;
            width: 100%;
            display: block !important;
        }

        .el-container:nth-child(5) .el-aside,
        .el-container:nth-child(6) .el-aside {
            line-height: 260px;
        }

        .el-container:nth-child(7) .el-aside {
            line-height: 320px;
        }

        .database {
            float: left;
        }

        .custom-tree-node {
            flex: 1;
            display: flex;
            align-items: center;
            justify-content: space-between;
            font-size: 14px;
            padding-right: 8px;
        }
    </style>
</head>
<body>
<div id="app">
    <el-container>
        <el-header>
            <lable class="el-form-item__label">应用程序名称：${applicationName}</lable>
        </el-header>
        <el-main>
            <el-select @change="dataChange" popper-class="database" v-model="value" placeholder="请选择">
                <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                </el-option>
            </el-select>
            <el-button @click="setCheckedKeys">选择</el-button>
            <el-button @click="generate">生成</el-button>
            <div style="padding: 10px 0">
                <el-tree :data="table"
                         show-checkbox
                         node-key="id"
                         ref="dataTableList"
                         accordion>
                </el-tree>
            </div>
        </el-main>
    </el-container>
</div>

<!-- import Vue before Element -->
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<!-- import JavaScript -->
<script src="https://unpkg.com/element-ui/lib/index.js"></script>
<script src="https://cdn.staticfile.org/vue-resource/1.5.1/vue-resource.min.js"></script>
<script type="text/javascript" src="/aes.js"></script>
<script type="text/javascript" src="/mode-ecb.js"></script>
<script type="text/javascript">
    new Vue({
        el: '#app',
        data: function () {
            return {
                options: [],
                value: '',
                table: [],
                keys: []
            }
        },
        mounted() {
            this.init()
        },
        methods: {
            init() {
                this.getDatabase()
            },
            dataChange(e) {
                this.getTable()
            },
            getCheckedKeys() {
                return this.$refs['dataTableList'].getCheckedKeys()
            },
            setCheckedKeys() {
                var that = this
                if (that.keys.length == 0) {
                    for (var i = 0; i < that.table.length; i++) {
                        var table = that.table[i]
                        that.keys.push(table.id)
                    }
                } else {
                    that.keys = []
                }
                that.$refs['dataTableList'].setCheckedKeys(that.keys);
            },
            getDatabase() {
                var that = this
                that.$http.post('getDatabase', {}).then(function (res) {
                    var json = this.decrypt(res.data)
                    var data = JSON.parse(json).data
                    that.value = data['tableSchema']
                    for (var i = 0; i < data.children.length; i++) {
                        var children = data.children[i]
                        that.options.push({value: children['tableSchema'], label: children['tableSchema']})
                    }

                    that.getTable();
                }, function (res) {
                    console.log('失败')
                })
            },
            getTable() {
                var that = this
                var json = this.encrypt("{\"tableSchema\": \"" + that.value + "\"}")
                that.$http.post('getDatabaseTableList', json).then(function (res) {
                    var json = this.decrypt(res.data)
                    console.log(json)
                    that.table = JSON.parse(json).data
                }, function (res) {
                    console.log('失败')
                })
            },
            generate() {
                var that = this
                var loading = that.$loading({
                    lock: true,
                    text: '生成中......',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.5)'
                });
                var data = []
                var keyList = that.getCheckedKeys();
                for (var i = 0; i < keyList.length; i++) {
                    var key = keyList[i]
                    data.push({"label": key})
                }
                var json = this.encrypt(data)
                that.$http.post('generate?dataBase=' + that.value, json).then(function (res) {
                    var json = this.decrypt(res.data)
                    var data = JSON.parse(json).data
                    if (data.code === 0) {
                        that.$message.success('生成成功！')
                    } else {
                        that.$message.error('生成失败：' + res.data.message)
                    }
                    loading.close()
                }, function (res) {
                    console.log('失败')
                    loading.close()
                })
            },
            encrypt(word) {
                var key = CryptoJS.enc.Utf8.parse('cn.framewiki.com')
                var iv = CryptoJS.enc.Utf8.parse('hk.framewiki.com')
                // 偏移量
                var encryption = CryptoJS.enc.Utf8.parse(word);
                // 算法
                var encrypted = CryptoJS.AES.encrypt(encryption, key,
                        {
                            iv: iv,
                            mode: CryptoJS.mode.CBC,
                            padding: CryptoJS.pad.ZeroPadding
                        });
                return CryptoJS.enc.Base64.stringify(encrypted.ciphertext);
            },
            /**
             * AES 解密 ：字符串 key iv  返回base64
             *
             */
            decrypt(word) {
                var key = CryptoJS.enc.Utf8.parse('cn.framewiki.com')
                var iv = CryptoJS.enc.Utf8.parse('hk.framewiki.com')
                var base64 = CryptoJS.enc.Base64.parse(word);
                var base64Value = CryptoJS.enc.Base64.stringify(base64);
                // AES解密
                var decrypt = CryptoJS.AES.decrypt(base64Value, key,
                        {
                            iv: iv,
                            mode: CryptoJS.mode.CBC,
                            padding: CryptoJS.pad.ZeroPadding
                        });
                return CryptoJS.enc.Utf8.stringify(decrypt).toString();
            }
        }
    })
</script>
</body>
</html>