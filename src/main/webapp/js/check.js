/**
 * 验证输入框的内容是否满足正则表达式，若不满足显示提示信息
 * @param textObj 输入框的id名
 * @param spanObj 错误提示的span标签的id名
 * @param errMassage 错误提示内容
 * @param regex 待匹配的正则 若没有输入，则默认为非负整数的正则规则
 * @returns 若匹配成功返回true 反之返回false
 */
function isValidation(textObj, spanObj, errMassage, regex){
	if(!isNotBlank(textObj, spanObj)){
		return false;
	}
	var value = $("#"+textObj).val();
	if (regex == "" || regex == null) {
		regex = num_regex;
	}
	if (!regex.test(value)) {
		$("#"+textObj).addClass("errText");
		$("#"+spanObj).text(errMassage);
		return false;
	}else{
		$("#"+textObj).removeClass("errText");
		$("#"+spanObj).text("");	
		return true;
	}
}
/**
 * 判断输入框输入的是否为正确的id
 * @param textObj 输入框的id名
 * @param spanObj 错误提示的span标签的id名
 * @param errMassage 错误提示内容
 * @returns 若是正确的id返回true，反之返回false
 */
function isId(textObj, spanObj, errMassage) {
	return isValidation(textObj, spanObj, errMassage, /^\d+$/);	
}
/**
 * 判断输入框输入的是否为正确的price
 * @param textObj 输入框的id名
 * @param spanObj 错误提示的span标签的id名
 * @param errMassage 错误提示内容
 * @returns 若是正确的price返回true，反之返回false
 */
function isPrice(textObj, spanObj, errMassage){
	return isValidation(textObj, spanObj, errMassage, /^[1-9]\d*\.\d*|0\.\d*[1-9]\d*|\d+$/);
}

/**
 * 判断输入框中的内容是否为空
 * @param textObj 输入框的id名
 * @param spanObj 错误提示的span标签的id名
 * @returns 若是为空返回false，反之返回false
 */
function isNotBlank(textObj, spanObj){
	var value = $("#"+textObj).val();
	if (!value) {
		$("#"+textObj).addClass("errText");
		$("#"+spanObj).text("该内容不能为空");
		return false;
	}else{
		$("#"+textObj).removeClass("errText");
		$("#"+spanObj).text("");	
		return true;
	}
}
/**
 * 检查待上传的文件是否有效
 * @param textObj 输入框的id名
 * @param spanObj 错误提示的span标签的id名
 * @returns 若文件有效返回true，反之则返回false
 */
function checkFile(textObj, spanObj){
	if(!isNotBlank(textObj, spanObj)){
		return false;
	}
	var val = $('#'+textObj).val().toLowerCase();
	
	if (val.length < 4) {
		$("#"+textObj).addClass("errText");
		$("#"+spanObj).text("请选择有效的图片");
		return false;
	}
	
	var suffix = val.substring(val.length-3);
	if(suffix == "jpg" || suffix == "png" || suffix == "gif"){
		$("#"+textObj).removeClass("errText");
		$("#"+spanObj).val("");
		return true;
	}else{
		$("#"+textObj).addClass("errText");
		$("#"+spanObj).text("请选择有效的图片");
		return false;
	}
}

/**
 * 主要用于添加书籍表单的提交验证
 * @returns 若没有错误则返回true，反之则返回false 
 */ 
function checkSubmitBook(){
	var hasNotBlank = isNotBlank('bookId', 'errBookId') && isNotBlank('bookPrice', 'errBookPrice') && isNotBlank('bookName', 'errBookName') 
					&& isNotBlank('bookPic', 'errBookPic') && isNotBlank('remarks', 'errRemarksSpan');
	var hasNum = isId('bookId', 'errBookId', '请输入数字编号') && isPrice('bookPrice', 'errBookPrice', '请输入正确的书籍价格');
	var hasCurrectFile = checkFile('bookPic', 'errBookPic');
	var massage;
	if (!hasNotBlank){
		massage = "请检查表单中是否有未填写的内容";
	}else if(!hasNum){
		massage = "请检查表单中是否有错误的格式";
	}else if(!hasCurrectFile){
		massage = "请检查上传文件格式是否有误";
	}
	if (hasNotBlank && hasNum && hasCurrectFile) {
		Swal.fire({
			type:"success",
			title:"添加成功",
			showConfirmButton: false,
			timer: 1500
		});
	}else{
		Swal.fire({
			type:"error",
			title:massage,
		});
		return false;
	}
}
/**
 * 主要用于修改书籍表单的提交验证
 * @returns 若没有错误则返回true，反之则返回false 
 */ 
function checkSubmitUpdateBook(){
	var hasNotBlank = isNotBlank('bookId', 'errBookId') && isNotBlank('bookPrice', 'errBookPrice') && isNotBlank('bookName', 'errBookName') 
					 && isNotBlank('remarks', 'errRemarksSpan');
	var hasNum = isId('bookId', 'errBookId', '请输入数字编号') && isPrice('bookPrice', 'errBookPrice', '请输入正确的书籍价格');
	var isPicModified = $("#isPicModified").val();
	var hasCurrectFile = true;
	if(isPicModified == 1){
		hasCurrectFile = checkFile('bookPic', 'errBookPic');
	}
	var massage;
	if (!hasNotBlank){
		massage = "请检查表单中是否有未填写的内容";
	}else if(!hasNum){
		massage = "请检查表单中是否有错误的格式";
	}else if(!hasCurrectFile){
		massage = "请检查上传文件格式是否有误";
	}
	if (hasNotBlank && hasNum && hasCurrectFile) {
		Swal.fire({
			type:"success",
			title:"修改成功",
			showConfirmButton: false,
			timer: 1500
		});
		return true;
	}else{
		Swal.fire({
			type:"error",
			title:massage,
		});
		return false;
	}
}