/**
 * 应用路径
 */
var path = yxui.rootPath();
var firstUrl = "";
var secondUrl = "";
/**
 * 测试环境
 */
firstUrl = path + '/base/uiData/menuMain.json';
secondUrl = path + '/base/uiData/tree_data{0}.json';
/**
 * 真实环境
 */
firstUrl = path + '/fa/privilege/resource/checkingAction!menuLink.action';
secondUrl = path + '/fa/privilege/resource/checkingAction!functionLink.action?pid={0}';
allUrl = path + '/fa/privilege/resource/checkingAction!userAllLink.action';