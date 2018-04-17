package com.demo.common;

/**
 * @author zWX454479 常量类
 */
public interface IopConst
{

    /**
     * 关键字/秘钥/键
     */
    String KEY = "key";

    /**
     * 文件
     */
    String FILE = "file";

    /**
     * 名称
     */
    String NAME = "name";

    /**
     * 筛选
     */
    String SELECTLIST = "selectList";

    /**
     * 密码key
     */
    String PASSWORD = "password";

    /**
     * 集合
     */
    String LIST = "list";

    String userList = "userList";

    /**
     * 
     */
    String CLOSE = "close";

    /**
     * 
     */
    String OPEN = "open";

    /**
     * 默认值
     */
    int DEFAULTNUM = 16;

    /**
     * 空字符串
     */
    String STRING_EMPTY = "";

    /**
     * 用户名 zwx516073 姓名
     */
    String USERNAME = "userName";

    /**
     * 用户账号 userAccount
     */
    String USERACCOUNT = "userAccount";

    /**
     * 上一次 登录失败的时间
     */
    String LASTFAILLOGIN = "lastFailLogin";

    /**
     * 部门
     */
    String DEPARTMENT = "department";

    /**
     * 工作
     */
    String JOB = "job";

    /**
     * 邮件
     */
    String EMAIL = "email";

    /**
     * 电话
     */
    String TELNUM = "telNum";

    /**
     * 备注
     */
    String REMARK = "remark";

    /**
     * base64编码
     */
    String BASE64 = "base64";

    /**
     * code
     */
    String CODE = "code";

    /**
     * 确认 或别选中
     */
    String NUM = "1";

    /**
     * 超级管理员ID
     */
    String SUPERID = "99999";

    /**
     * 创建时间
     */
    String CREATETIME = "createTime";

    /**
     * 到期时间 zwx516073
     */
    String EXPIRYDATE = "expiryDate";

    /**
     * 用户Id
     */
    String USERID = "userID";

    /**
     * 运营商ID
     */
    String CUSTOMERID = "customerID";

    /**
     * 国家ID
     */
    String AREAID = "areaID";

    /**
     * 前台点击导航页码的次数
     */
    String LIMIT = "limit";

    /**
     * 国家
     */
    String COUNTRY = "country";

    // roleID areaID

    /**
     * 角色ID
     */
    String ROLEID = "roleID";

    /**
     * 运营商名
     */
    String CUSTOMERNAME = "customerName";

    /**
     * 建议
     */
    String SUGGESTION = "suggestion";

    /**
     * 用于模糊查询的字符串拼接
     */
    String PERCENT = "%";

    /**
     * 冒号
     */
    String COLON = ":";

    /**
     * 用于前台传输过来的key，代表当前页码
     */
    String PAGENUM = "pageNum";

    /**
     * 前台点击导航页码的次数
     */
    String PAGECOUNT = "pageCount";

    /**
     * 每页展示条数
     */
    String PAGENUMBER = "pageNumber";

    /**
     * 起始页码
     */
    String START = "start";

    /**
     * 终止页码
     */
    String END = "end";

    /**
     * JSON的值
     */
    String REQUEST_JSON = "0X00000011";

    /**
     * 用户值
     */
    String USER = "0X00000012";

    /**
     * 错误码
     */
    String EXECUTE = "0X00000013";

    /**
     * 状态
     */
    String STATUES = "0X00000014";

    String SERVICEID = "0X00000015";

    String DELETE = "0X00000016";

    String IOP_RUN_SUCCESS = "1-FFF-0-000";

    /**
     * 运行失败
     */
    String IOP_RUN_FAILSE = "1-FFF-1-FFF";

    /**
     * 英语
     */
    String LANG_EN = "en";

    /**
     * 中文
     */
    String LANG_ZH = "zh";

    /**
     * 前台传入的语言
     */
    String LANG = "lang";

    /**
     * 错误码
     */
    String STATUSCODE = "statusCode";

    /**
     * 逗号
     */
    String COMMA = ", ";

    /**
     * 类型
     */
    String TYPE = "type";

    /**
     * 第一层ID
     */
    String FIRSTID = "firstID";

    /**
     * 执行动作
     */
    String ACTIONTYPE = "actionType";

    /**
     * 时间格式yyyyMM
     */
    String YYYYMMFORMAT = "yyyyMM";

    /**
     * 语言
     */
    String LANGUAGE = "language";

    /**
     * 动作
     */
    String ACTION = "action";

    /**
     * 删除
     */
    String DEL = "del";

    /**
     * 增加
     */
    String ADD = "add";

    /**
     * 修改
     */
    String MODIFY = "modify";

    /**
     * 总数
     */
    String TOTAL = "total";

    /**
     * 总数
     */
    String COUNT = "count";

    /**
     * utf-8的编码格式
     */
    String UTF = "UTF-8";

    /**
     * 附件url
     */
    String URL = "URL";

    /**
     * 标题
     */
    String TITLE = "title";

    /**
     * AES加密算法的KEY
     */
    String AESKEY = "!QAZ@WSX#EDC$RFV";

    /**
     * DES加密算法的KEY
     */
    String DESKEY = "QWEqwe!@~";

    /**
     * app渠道
     */
    String APP = "app";

    /**
     * 其他渠道
     */
    String OTHER = "其他";

    /**
     * 微信渠道
     */
    String WX = "微信";

    /**
     * 掌厅渠道
     */
    String ZT = "掌厅";

    /**
     * 消息推送渠道
     */
    String MSG = "消息推送";

    /**
     * 错误的文件
     */
    String ERRORFILE = "errorFile";

    /**
     * 多个base64图片组成的JSONArray
     */
    String PICBASE = "picBase64Info";

    /**
     * 常用枚举
     */
    interface Enums
    {
        /**
         * @author zWX454479 actionType 操作类型
         * 0保存，1添加，2修改，3删除，4设置为固定，5设置为临时
         */
        public enum ActionType {
            Save("0"), Add("1"), Modify("2"), Del("3"),Fix("4"),Temp("5");

            private String value;

            private ActionType(String num)
            {
                this.value = num;
            }

            public String toValue()
            {
                return value;
            }

            public int toNum()
            {
                return Integer.parseInt(value);
            }
        }

    }

    /**
     * @author zWX454479 特殊的状态码
     */
    interface SpecialStatusCode
    {

        /**
         * 无数据
         */
        String IOP_RUN_NODATA = "1-FFF-1-001";

        /**
         * 错误的参数
         */
        String IOP_RUN_ERRORPARAM = "1-FFF-1-002";

        /**
         * 错误的格式
         */
        String IOP_RUN_ERRORFORMART = "1-FFF-1-003";

        /**
         * 错误的文件大小
         */
        String IOP_RUN_ERRORFILESIZE = "1-FFF-1-004";

        /**
         * 沒有权限
         */
        String IOP_RUN_NOPERMISSION = "1-FFF-1-005";

        /**
         * 组件名重复
         */
        String IOP_RUN_ERRORNAME = "1-FFF-1-006";

        /**
         * 日期不连续
         */
        String IOP_RUN_NOCONTINUOUS = "1-FFF-1-007";

        /**
         * 数据重复
         */
        String IOP_RUN_ERRORDATE = "1-FFF-1-008";

        /**
         * 日趋格式不对
         */
        String IOP_RUN_ERRORDATEFORMAT = "1-FFF-1-009";

        /**
         * 名称错误
         */
        String IOP_RUN_NONAME = "1-FFF-1-010";

        /**
         * 存在空值
         */
        String IOP_RUN_NANVALUE = "1-FFF-1-011";

        /**
         * 模板错误
         */
        String IOP_RUN_ERRORTEMPLATE = "1-FFF-1-012";

        /**
         * 验证码 错误
         */
        String IOP_YANZHENGMA_FAILSE = "1-FFF-2-001";

        /**
         * 用户名或密码错误
         */
        String IOP_USERORPASSWORD_FAILSE = "1-FFF-2-002";
        
        /**
         * 请求时间超时
         */
        String IOP_TIMEOUT_FAILSE = "1-FFF-2-003";
        
        /**
         * id、key不匹配
         */
        String IOP_IDORKEY_FAILSE = "1-FFF-2-004";
        
        
        /**
         * token 验证错误
         */
        String IOP_TOKEN_FAILSE = "1-FFF-2-005";
        
       
        /**
         *  sign", "签名错误
         */
        String IOP_SIGN_FAILSE = "1-FFF-2-006";

    }

    interface Area
    {

        /**
         * 国家
         */
        String COUNTRY = "country";

        /**
         * 省
         */
        String PROVINCE = "province";

        /**
         * 市
         */
        String CITY = "city";

        /**
         * 国家集合
         */
        String COUNTRYLIST = "countryList";

        /**
         * 省集合
         */
        String PROVINCELIST = "provinceList";

        /**
         * 市集合
         */
        String CITYLIST = "cityList";

    }

    /**
     * @author wWX415219
     * 
     */
    interface Mail
    {

        /**
         * 邮箱登录账号
         */
        String USER = "pmail_VideoBPDO";

        /**
         * 邮箱登录密码
         */
        String PASSWORD = "@sGpE6-W";

        /**
         * 邮箱发送账号
         */
        String FROMADDRESS = "videobpdo@huawei.com";

        /**
         * 邮件配置参数
         */
        String AUTH = "mail.smtp.auth";

        String AUTHVAL = "true";

        /**
         * 邮件配置参数
         */
        String PROTOCOL = "mail.transport.protocol";

        String PROTOCOLVAL = "smtp";

        /**
         * 邮件配置参数
         */
        String HOST = "mail.host";

        String HOSTVAL = "smtp.huawei.com";

        /**
         * 邮箱正则表达式
         */
        String MAILRREGEX = "[a-zA-Z0-9_\\-\\.]+@huawei.com";

        /**
         * 手机号正则表达式
         */
        String MOBILEREGEX = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[6-8])|(18[0,5-9]))\\d{8}$";

        /**
         * 短信APPID
         */
        String APPID = "EIPSMS0015";

        /**
         * 短信APPKEY
         */
        String APPKEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJmG4thprRs2A13d53A3VIUhmA+pocQ4pwu1oNMwYSZW3RcrEomvkMD+pTceUq2vK1Nyx2qtdaTBpjduv+3X9Eq/IChZKxoDpkxOLh/riXLKI3VJKYhkCk3nptxxBg4Sy0lJosfhK1IhCsKzrcFdtYYXGHb0/U6PVsUAxgBtkDkzAgMBAAECgYBZKC8WdJA1zDE6UsKztT4dLjwrZFxwImJH94tU+bSBWlJ8wOO8F7vdiEQT9UUYXjCv/mj2d5NpQZgwet1010OjOBXmXMQoofDrH95XPUir1y7scOOQ7FhPcNYJt4GYi6fjdmBYqr86Rob2cDeY7KL0hXmSQeF8pMUeaF80ernJmQJBAOVIK+xzZE+kz3eX9aHChxV1VtcKVa2JYivZgd0OnytIXw7NVDSxiKwK0MFMwFBf7OFvEm0BYVI1yIJTctaUET0CQQCratO4ALBCxRj8fJjwofcN37AzjYhQS13fdKD/7gr2Oc/JXFl3kQvy+hYuQdAWzJnRno0yKeekBHcWAmDAiDsvAkB/aYrXHAy7YYfVGsHqQVKV7x3x5BFWnUjLu1Q6RrzW7aG9dQb9PUcI6/2z3XXldFkLsZCYuLKfX7nsCnl/msq1AkA3W9emK+rAlYCdQevugpAT1fLEo2erbruRR8wa+xhlLoRxZjaqi5hNTwDMzYpTYp4meUrmMWxSpvKClCAE7boXAkBr4mZdefQ0ZDj9ttXkAZI6+x8Vp81o43uzZGFTJSNnOTmh5S0p8WJoDWtJBgPJuMz0P0faUxE1ujHoIYnu7lqo";

        /**
         * 主题
         */
        String SUBJECT = "subject";

        /**
         * 收件人
         */
        String ADDRESSEE = "addressee";

        /**
         * 邮件内容
         */
        String CONTENT = "content";

    }

    /**
     * 地区名
     */
    String AREANAME = "areaName";

    interface Activity
    {
        /**
         * 活动管理主键
         */
        String ID = "id";

        /**
         * 选择区域
         */
        // String AREAID="areaID";

        /**
         * 活动ID
         */
        String ACTIVITYID = "activityID";

        /**
         * 活动名称
         */
        String ACTIVITYNAME = "activityName";

        /**
         * 活动注入时间
         */
        String INJECTTIME = "injectTime";

        /**
         * 活动起始时间
         */
        String BEGINTIME = "beginTime";

        /**
         * 活动结束时间
         */
        String ENDTIME = "endTime";

        /**
         * 活动URL
         */
        String ACTIVITYURL = "activityURL";

        /**
         * 活动详情
         */
        String DETAILS = "details";

        /**
         * 路径
         */
        String PATH = "path";

        /**
         * 活动详情
         */
        String ACTIVITYARRAY = "activityArray";

        String TEMPLATEID = "templateID";

        /**
         * 上传文件格式错误
         */
        String IOP_RUN_ERRFORMAT = "1-FFF-1-021";
    }

    /**
     * 地址集合
     */
    String AREAARRARY = "areaArray";

    /**
     * 操作人ID
     */
    String OPERATORID = "operatorID";

    /**
     * 运营事件对象
     */
    String EVENT = "event";

    /**
     * 跟踪对象
     */
    String TRACKING = "tracking";

}
