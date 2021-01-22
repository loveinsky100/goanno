
//
// Method1
// @Description:
//
func Method1() {
}

//
// Method1_1
// @Description:
// @return error
//
func Method1_1() error {
}

//
// Method1_2
// @Description:
// @param val
// @return error
//
func Method1_2(val Type) error {
}

//
// Method1_3
// @Description:
// @param val
// @return err
//
func Method1_3(val Type) (err error) {
}

//
// Method2
// @Description:
// @return string
// @return ss
//
func Method2()  (string, ss func(key interface{}) bool)  {
}

//
// Method3
// @Description:
// @param unixStamp
// @param pattern
// @return string
// @return value
// @return *error
//
func Method3(unixStamp int64, pattern string) (string, value *string, *error) {
}

//
// Method4
// @Description:
// @receiver demo
// @param unixStamp
// @param pattern
// @return string
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
// @Description:
// @receiver this
// @param arg1
// @param arg2
// @return error
//
func (this *TestIndex) Method5( arg1 Type, arg2 Type ) error {
	this.Data["json"] = "hello, world"
	this.ServeJSON()
}

//
// Method6
// @Description:
// @receiver this
// @return err
// @return val
//
func (this *TestIndex) Method6() (err error, val Type){
}

//
// Method6
// @Description:
// @receiver this
// @param in
// @param in2
// @return err
// @return val
//
func (this *TestIndex)Method6(in Typ11, in2 Typeee)(err error, val Type){
}

//
// Method8
// @Description:
// @receiver this
// @return error
//
func (this *TestIndex) Method8()error{
}

//
// Method9
// @Description:
// @receiver this
// @return error
//
func(this *TestIndex)Method9()error{
}

//
// Method10
// @Description:
// @receiver this
// @param arg1
// @return error
//
func (this *TestIndex) Method10(arg1 Type)error{
}

//
// Method11
// @Description:
// @receiver this
// @param arg1
// @return error
//
func(this *TestIndex)Method11(arg1 Type)error{
}

//
// DoService1
// @Description:
//
// start_interface_method
DoService1()
// end_interface_method

//
// DoService2
// @Description:
// @return error
//
// start_interface_method
DoService2() error
// end_interface_method

//
// DoService3
// @Description:
// @return Type
// @return a
//
// start_interface_method
DoService3() (Type, a Type)
// end_interface_method

//
// DoService4
// @Description:
// @param in
// @return Type
// @return a
//
// start_interface_method
DoService4(in Type) (Type, a Type)
// end_interface_method

//
// DoService5
// @Description:
// @param in
// @param in2
// @return Type
// @return a
//
// start_interface_method
DoService5(in Type, in2) (Type, a Type)
// end_interface_method