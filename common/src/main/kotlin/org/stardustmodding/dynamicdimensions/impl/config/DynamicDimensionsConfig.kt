/*
 * Copyright (c) 2021-2023 Team Galacticraft
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.stardustmodding.dynamicdimensions.impl.config

interface DynamicDimensionsConfig {
    fun applyDefaultValues() {
        allowDimensionCreation(true)
        deleteRemovedDimensions(false)
        deleteDimensionsWithPlayers(false)
        enableCommands(false)
        commandPermissionLevel(2)
    }

    fun allowDimensionCreation(): Boolean

    fun deleteRemovedDimensions(): Boolean

    fun deleteDimensionsWithPlayers(): Boolean

    fun enableCommands(): Boolean

    fun commandPermissionLevel(): Int

    fun allowDimensionCreation(value: Boolean)

    fun deleteRemovedDimensions(value: Boolean)

    fun deleteDimensionsWithPlayers(value: Boolean)

    fun enableCommands(value: Boolean)

    fun commandPermissionLevel(value: Int)
}
