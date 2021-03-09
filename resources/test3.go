// Template Declare Start

//
// ${function_name}
// @Description:${todo}
// @receiver ${receiver_name_type}
// @param ${param_name_type}
// @return ${ret_name_type}
//

// Template Declare End


// Methods Declare

//
// Method1_4
// @Description:
// @param val Type
// @param val2 Type
// @return error
//
func Method1_4(val, val2 Type) error {
}

//
// Method1
// @Description:
//
func Method1() {
}

//
// Method1_1
// @Description:
// @return func(int64) error
//
func Method1_1() func(int64) error {
}

//
// Method1_2
// @Description:
// @param val Type
// @return error
// @return func(int64) error
//
func Method1_2(val Type) (error, func(int64) error) {
}

//
// Method1_3
// @Description:
// @param val Type
// @return error
// @return func(int64) error
// @return func(int64, int32) (error, func(Type) int64
//
func Method1_3(val Type) (error, func(int64) error, func(int64, int32) (error, func(Type) int64) {
}

//
// Method1_4
// @Description:
// @param val Type
// @param handler func(int64) error
// @return error
// @return func(int64) error
// @return func(int64, int32) (error, func(Type) int64
//
func Method1_4(val Type, handler func(int64) error) (error, func(int64) error, func(int64, int32) (error, func(Type) int64) {
}

//
// Method1_4
// @Description:
// @param val Type
// @param handler func(int64) error
// @param handler2 func(int64, int32) int32
// @return error
// @return func(int64) error
// @return func(int64, int32) (error, func(Type) int64
//
func Method1_4(val Type, handler func(int64) error, handler2 func(int64, int32) int32) (error, func(int64) error, func(int64, int32) (error, func(Type) int64) {
}

//
// Method1_4
// @Description:
// @param val Type
// @param val2 Type
// @return error
//
func Method1_4(val, val2 Type) error {
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
// @return t Type
// @return a func (int32, int64) error
//
// start_interface_method
DoService3() (t Type, a func (int32, int64) error)
// end_interface_method