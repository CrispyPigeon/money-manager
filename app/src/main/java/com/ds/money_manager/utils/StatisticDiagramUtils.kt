package com.ds.money_manager.utils

import android.graphics.Color
import app.futured.donut.DonutSection
import com.ds.money_manager.data.model.api.StatisticItemResponse

object StatisticDiagramUtils {
    fun setDiagramData(statisticItems: List<StatisticItemResponse>) : List<DonutSection> {
        val sections = mutableListOf<DonutSection>()

        statisticItems.forEach {
            sections.add(
                DonutSection(
                    name = it.name,
                    color = Color.parseColor(it.color),
                    amount = it.amount.toFloat()
                )
            )
        }

        return sections
    }
}