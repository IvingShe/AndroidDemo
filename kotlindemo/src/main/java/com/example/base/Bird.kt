package com.example.base

/**
 * author：Iving
 * date：2021/8/15 21:43
 * description：
 *  基类
 */
/***
 * open represents the class can be inherited
 */
open class Bird(var name: String, val sex : Int= MALE) {


    /***
     * member variables
     */
    var sexName:String

    init {
        sexName =getSexName(sex)
    }

    /***
     * member functions
     *   open represents the function can be overridden
     */
   open fun getSexName(sex:Int):String{
        var sexName = when (sex){
            MALE->"公"
            FEMALE->"母"
            else->"未知"
        }
        return sexName;
    }

    fun getDes(tag:String):String{
        var des:String ="Welcome to ${tag}! This is ${name} and sex is ${sexName}"
        return des
    }

    /***
     * companion class
     */
    companion object StaticBird {
        val MALE: Int = 1
        val FEMALE: Int = 2
        val UNKNOWN: Int = -1
        fun judgeSex(sexName:String):Int{
            var sex= when(sexName){
                "公","雄"-> MALE
                "母","雌"-> FEMALE
                else-> UNKNOWN
            }
            return sex
        }

    }
}