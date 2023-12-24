package io.huvz.client

import java.time.LocalDate
import java.time.temporal.ChronoUnit

// 班级信息

object nowTime{
    fun calculateWeeks(startDate: LocalDate, endDate: LocalDate): Long {
        val days = ChronoUnit.DAYS.between(startDate, endDate)
        val remainingDays = days % 7

        return days / 7 + if (remainingDays > 0) 1 else 0
    }
    fun getcurrentWeek():Int{
        // 计算当前周次
        val currentDate = LocalDate.now()
        val startDate: LocalDate
        val endDate: LocalDate
        val currentYear: Int
        val currentSemesterNumber: Int
        // 根据当前日期确定学年和学期
        if (currentDate.monthValue >= 9) {
            //第一个学期从9月1日开始计算
            currentYear = currentDate.year
            currentSemesterNumber = 3
            startDate = LocalDate.of(currentYear, 9, 1)
            endDate = LocalDate.of(currentYear + 1, 2, 28)
        } else {
            //第二个学期就从2月10日开始计算到8月31
            currentYear = currentDate.year - 1
            currentSemesterNumber = 12
            startDate = LocalDate.of(currentYear, 2, 10)
            endDate = LocalDate.of(currentYear, 8, 31)
        }
        val week = calculateWeeks(startDate, currentDate).toInt()

        return week
    }
    fun getcurrentYear():Int{
        // 计算当前周次
        val currentDate = LocalDate.now()
        val startDate: LocalDate
        val endDate: LocalDate
        val currentYear: Int
        val currentSemesterNumber: Int
        // 根据当前日期确定学年和学期
        if (currentDate.monthValue >= 9) {
            //第一个学期从9月1日开始计算
            currentYear = currentDate.year
        } else {
            //第二个学期就从2月10日开始计算到8月31
            currentYear = currentDate.year - 1
        }

        return currentYear
    }
    fun getSemesterNumber():Int{
        // 计算当前周次
        val currentDate = LocalDate.now()
        val startDate: LocalDate
        val endDate: LocalDate
        val currentYear: Int
        val currentSemesterNumber: Int
        // 根据当前日期确定学年和学期
        if (currentDate.monthValue >= 9) {
            //第一个学期从9月1日开始计算
            currentYear = currentDate.year
            currentSemesterNumber = 3
            startDate = LocalDate.of(currentYear, 9, 1)
            endDate = LocalDate.of(currentYear + 1, 2, 28)
        } else {
            //第二个学期就从2月10日开始计算到8月31
            currentYear = currentDate.year - 1
            currentSemesterNumber = 12
            startDate = LocalDate.of(currentYear, 2, 10)
            endDate = LocalDate.of(currentYear, 8, 31)
        }
        val week = ChronoUnit.WEEKS.between(startDate, currentDate).toInt()

        return currentSemesterNumber
    }
}