package com.cdkjframework.constant;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.datasource.mybatispro.consts
 * @ClassName: StringConstant
 * @Description: 字条串池
 * @Author: xiaLin
 * @Date: 2025/2/6 17:36
 * @Version: 1.0
 */
public interface StringConstant {

	/**
	 * 连接符
	 */
	String AMPERSAND = "&";

	/**
	 * 且
	 */
	String AND = "and";

	/**
	 * @
	 */
	String AT = "@";

	/**
	 * 星号
	 */
	String ASTERISK = "*";

	/**
	 * 星号
	 */
	String STAR = ASTERISK;

	/**
	 * 反斜杠
	 */
	String BACK_SLASH = "\\";

	/**
	 * 冒号
	 */
	String COLON = ":";

	/**
	 * 逗号
	 */
	String COMMA = ",";
	/**
	 * 减号
	 */
	String DASH = "-";
	/**
	 * 美元符号
	 */
	String DOLLAR = "$";
	/**
	 * 点
	 */
	String DOT = ".";
	/**
	 * 点点
	 */
	String DOTDOT = "..";
	/**
	 * 点class
	 */
	String DOT_CLASS = ".class";
	/**
	 * 点java
	 */
	String DOT_JAVA = ".java";
	/**
	 * 点xml
	 */
	String DOT_XML = ".xml";

	/**
	 * 空字符串
	 */
	String EMPTY = "";
	/**
	 * 等号
	 */
	String EQUALS = "=";
	/**
	 * false
	 */
	String FALSE = "false";

	/**
	 * 斜杠
	 */
	String SLASH = "/";
	/**
	 * #
	 */
	String HASH = "#";
	/**
	 * ^
	 */
	String HAT = "^";
	/**
	 * {
	 */
	String LEFT_BRACE = "{";
	/**
	 * (
	 */
	String LEFT_BRACKET = "(";
	/**
	 * <
	 */
	String LEFT_CHEV = "<";
	/**
	 * ,
	 */
	String DOT_NEWLINE = ",\n";
	/**
	 * \n
	 */
	String NEWLINE = "\n";

	/**
	 * n
	 */
	String N = "n";

	/**
	 * no
	 */
	String NO = "no";

	/**
	 * null
	 */
	String NULL = "null";

	/**
	 * NUM
	 */
	String NUM = "NUM";

	/**
	 * off
	 */
	String OFF = "off";
	/**
	 * on
	 */
	String ON = "on";
	/**
	 * %
	 */
	String PERCENT = "%";
	/**
	 * |
	 */
	String PIPE = "|";
	/**
	 * +
	 */
	String PLUS = "+";
	/**
	 * ?
	 */
	String QUESTION_MARK = "?";
	/**
	 * !
	 */
	String EXCLAMATION_MARK = "!";
	/**
	 * "
	 */
	String QUOTE = "\"";
	/**
	 * \r
	 */
	String RETURN = "\r";
	/**
	 * \t
	 */
	String TAB = "\t";
	/**
	 * 右大括号
	 */
	String RIGHT_BRACE = "}";
	/**
	 * 右括号
	 */
	String RIGHT_BRACKET = ")";

	/**
	 * >
	 */
	String RIGHT_CHEV = ">";
	/**
	 * 分号
	 */
	String SEMICOLON = ";";
	/**
	 * 单引号
	 */
	String SINGLE_QUOTE = "'";

	/**
	 * 反引号
	 */
	String BACKTICK = "`";
	/**
	 * 空格
	 */
	String SPACE = " ";
	/**
	 * sql
	 */
	String SQL = "sql";
	/**
	 * 破折号
	 */
	String TILDA = "~";
	/**
	 * [
	 */
	String LEFT_SQ_BRACKET = "[";

	/**
	 * ]
	 */
	String RIGHT_SQ_BRACKET = "]";

	/**
	 * true
	 */
	String TRUE = "true";
	/**
	 * _
	 */
	String UNDERSCORE = "_";

	/**
	 * UTF-8
	 */
	String UTF_8 = "UTF-8";

	/**
	 * ASCII
	 */
	String US_ASCII = "US-ASCII";
	/**
	 * ISO-8859-1
	 */
	String ISO_8859_1 = "ISO-8859-1";

	/**
	 * y 是
	 */
	String Y = "y";
	/**
	 * yes 是
	 */
	String YES = "yes";
	/**
	 * 1
	 */
	String ONE = "1";
	/**
	 * 0
	 */
	String ZERO = "0";

	/**
	 * ${
	 */
	String DOLLAR_LEFT_BRACE = "${";
	/**
	 * #{}
	 */
	String HASH_LEFT_BRACE = "#{";

	/**
	 * 换行
	 */
	String CRLF = "\r\n";

	/**
	 * HTML 空格
	 */
	String HTML_NBSP = "&nbsp;";

	/**
	 * HTML 标签
	 */
	String HTML_AMP = "&amp";
	/**
	 * HTML 标签
	 */
	String HTML_QUOTE = "&quot;";
	/**
	 * HTML 标签
	 */
	String HTML_LT = "&lt;";
	/**
	 * HTML 标签
	 */
	String HTML_GT = "&gt;";

	/**
	 * 空数组
	 */
	String[] EMPTY_ARRAY = new String[0];

	/**
	 * 换行符的 {@code byte} 数组
	 */
	byte[] BYTES_NEW_LINE = StringConstant.NEWLINE.getBytes();
}
