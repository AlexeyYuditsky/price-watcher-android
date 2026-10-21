package alexeyyuditsky.pricewatcher.navigation

internal data object TrackingDestination : AppDestination {

    private fun readResolve(): Any = TrackingDestination
}