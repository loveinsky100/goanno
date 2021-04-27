// Template Declare Start

//
// ${function_name}
// @Description:${todo}
// @receiver ${receiver}
// @param ${params}
// @return ${return_types}
//

// Template Declare End


// Methods Declare

//
// Method1
// @Description: 测试方法1
//
func Method1() {
}

//
// Method1_1
// @Description: 测试方法2
// @return error 错误信息
//
func Method1_1() error {
}

//
// Method1_2
// @Description: 测试方法3
// @param val 参数
// @return error 错误信息
//
func Method1_2(val Type) error {
}

//
// Method1_3
// @Description: test method 3
// @param val some value
// @return err error message
//
func Method1_3(val Type) (err error) {
}

//
// Method2
// @Description: test method 4
// @return string some value
// @return ss some method
//
func Method2()  (string, ss func(key interface{}) bool)  {
}

//
// Method3
// @Description:
// @param unixStamp: time
// @param pattern: mode
// @return string some value
// @return value hello world
// @return *error message
//
func Method3(unixStamp int64, pattern string) (string, value *string, *error) {
}

//
// Method4
// @Description: test
// @receiver demo test2
// @param unixStamp test3
// @param pattern test4
// @return string test5
//
func (demo *
string) Method4(unixStamp
int64, pattern
 string)
 string {
	return time.Unix(unixStamp, 0).Format(pattern)
}

//
// Method5
// @Description: test 1
// @receiver this test 2
// @param arg1 test 3
// @param arg2 test 4
// @return error test 5
//
func (this *TestIndex) Method5( arg1 Type, arg2 Type ) error {
	this.Data["json"] = "hello, world"
	this.ServeJSON()
}

//
// Method6
// @Description: test 1
// @receiver this test 2
// @return err test 3
// @return val test 4
//
func (this *TestIndex) Method6() (err error, val Type){
}

//
// Method6
// @Description: test 1
// @receiver this test 2
// @param in test 3
// @param in2 test 5
// @return err test 4
// @return val test 56
//
func (this *TestIndex)Method6(in Typ11, in2 Typeee)(err error, val Type){
}

//
// Method8
// @Description: test
// @receiver this test
// @return error test
//
func (this *TestIndex) Method8()error{
}

//
// Method9
// @Description: test1
// @receiver this test 2
// @return error test 3
//
func(this *TestIndex)Method9()error{
}

//
// Method10
// @Description: test 2
// @receiver this test 3
// @param arg1 test 3
// @return error test1
//
func (this *TestIndex) Method10(arg1 Type)error{
}

//
// Method11
// @Description: test 1
// @receiver this test2
// @param arg1 test 3
// @return error test 4
//
func(this *TestIndex)Method11(arg1 Type)error{
}

//
// DoService1
// @Description: test22
//
// start_interface_method
DoService1()
// end_interface_method

//
// DoService2
// @Description: test 33
// @return error test  testtest
//
// start_interface_method
DoService2() error
// end_interface_method

//
// DoService3
// @Description: test 2222
// @return Type test 333
// @return a test 444
//
// start_interface_method
DoService3() (Type, a Type)
// end_interface_method

//
// DoService4
// @Description: test 22
// @param in test222
// @return Type test33
// @return a test33333
//
// start_interface_method
DoService4(in Type) (Type, a Type)
// end_interface_method

//
// DoService5
// @Description: test22222
// @param in test1111
// @param in2 test111111
// @return Type testerrr
// @return a test2222222222
//
// start_interface_method
DoService5(in Type, in2) (Type, a Type)
// end_interface_method