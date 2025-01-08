package com.example.plantapp.domain.auht.repository.tips
class DailyTipsRepositoryImpl : DailyTipsRepository {
    private val tips = listOf(
        "Water your plants early in the morning.",
        "Avoid overwatering your plants.",
        "Ensure enough light for indoor plants.",
        "Use natural compost for fertilization.",
        "Check leaves regularly for pests.",
        "Rotate your plants occasionally to ensure even growth.",
        "Dust off the leaves of your plants to help them photosynthesize better.",
        "Use rainwater for watering whenever possible.",
        "Avoid placing plants in direct drafts or near air conditioners.",
        "Group plants together to increase humidity for tropical plants.",
        "Trim dead or yellowing leaves to promote new growth.",
        "Don’t repot plants too often; it can stress them.",
        "Make sure pots have drainage holes to avoid waterlogging.",
        "Match the plant's watering needs to the season; water less in winter.",
        "Keep succulents in bright, indirect light for best growth.",
        "Use coffee grounds sparingly as a fertilizer for acid-loving plants.",
        "Inspect the soil regularly to check for dryness before watering.",
        "Avoid fertilizing plants during their dormant period.",
        "Place indoor plants near windows, but avoid intense direct sunlight.",
        "Quarantine new plants before introducing them to your collection to prevent pest infestations."
    )

    override fun getDailyTip(): String {
        return tips.random()
    }
}
