package org.stardustmodding.skyengine.math

import kotlin.math.pow

object Wrappers {
    /**
     * Calculate velocity.
     *
     * @param[deltaSpeed] The change in speed. Unit: m/s^2
     * @param[deltaTime] The change in time - commonly the time since the last frame. Unit: seconds
     * @return The velocity. Unit: m/s
     */
    fun vel(deltaSpeed: Float, deltaTime: Float) = deltaSpeed / deltaTime

    /**
     * Calculate acceleration.
     *
     * @param[deltaVel] The change in velocity. Unit: m/s^2
     * @param[deltaTime] The change in time - commonly the time since the last frame. Unit: seconds
     * @return The acceleration. Unit: m/s^2
     */
    fun accel(deltaVel: Float, deltaTime: Float) = deltaVel / deltaTime

    /**
     * Calculate an object's weight.
     *
     * @param[mass] The object's mass. Unit: kg
     * @param[grav] The gravity constant - commonly around 9.82. Unit: m/s^2
     * @return The object's weight. Unit: Newtons
     */
    fun weight(mass: Float, grav: Float) = mass * grav

    /**
     * Calculate the force applied to an object.
     *
     * @param[mass] The object's mass. Unit: kg
     * @param[accel] The acceleration. Unit: m/s^2
     * @return The force applied to the object. Unit: Newtons
     */
    fun force(mass: Float, accel: Float) = mass * accel

    /**
     * Calculate the momentum of an object.
     *
     * @param[mass] The object's mass. Unit: kg
     * @param[vel] The velocity. Unit: m/s^2
     * @return The momentum. Unit: kg * m/s
     */
    fun momentum(mass: Float, vel: Float) = mass * vel

    /**
     * Calculate the impulse applied to an object.
     *
     * @param[force] The force applied to an object. Unit: Newtons
     * @param[deltaTime] The change in time - commonly the time since the last frame. Unit: seconds
     * @return The impulse applied to the object. Unit: N * s
     */
    fun impulse(force: Float, deltaTime: Float) = force * deltaTime

    /**
     * Calculate the density of a medium.
     *
     * @param[mass] The mass of the medium. Unit: grams
     * @param[volume] The volume of the medium. Unit: cm^3
     * @return The density of the medium. Unit: g / cm^3
     */
    fun density(mass: Float, volume: Float) = mass / volume

    /**
     * Calculate the friction force.
     *
     * @param[coef] The friction coefficient.
     * @param[normForce] The normal force. Unit: Newtons
     * @return The friction force. Unit: Newtons
     */
    fun friction(coef: Float, normForce: Float) = coef * normForce

    /**
     * Calculate pressure.
     *
     * @param[force] The force. Unit: Newtons
     * @param[area] The area. Unit: m^2
     * @return The pressure. Unit: Pascals
     */
    fun pressure(force: Float, area: Float) = force / area

    /**
     * Calculate drag.
     *
     * @param[density] The density of the fluid. Unit: kg / m^3
     * @param[velocity] The velocity of the object. Unit: m/s
     * @param[coef] The drag coefficient.
     * @param[area] The cross-sectional area. Unit: m^2
     */
    fun drag(density: Float, velocity: Float, coef: Float, area: Float) =
        (1 / 2) * density * velocity.pow(2) * coef * area

    /**
     * Calculate the drag coefficient.
     *
     * @param[force] The drag force.
     * @param[density] The mass density of the fluid.
     * @param[flowSpeed] The flow speed of the object (relative to the fluid).
     * @param[area] The reference area. Unit: m^2
     */
    fun dragCoef(force: Float, density: Float, flowSpeed: Float, area: Float) =
        (2 * force) / (density * flowSpeed.pow(2) * area)
}
