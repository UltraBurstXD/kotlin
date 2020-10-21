/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.ir.declarations.persistent

import org.jetbrains.kotlin.descriptors.DescriptorVisibility
import org.jetbrains.kotlin.ir.declarations.*
import org.jetbrains.kotlin.ir.declarations.persistent.carriers.Carrier
import org.jetbrains.kotlin.ir.declarations.persistent.carriers.PropertyCarrier
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource

internal abstract class PersistentIrPropertyCommon(
    override val startOffset: Int,
    override val endOffset: Int,
    origin: IrDeclarationOrigin,
    override val name: Name,
    override var visibility: DescriptorVisibility,
    override val isVar: Boolean,
    override val isConst: Boolean,
    override val isLateinit: Boolean,
    override val isDelegated: Boolean,
    override val isExternal: Boolean,
    override val isExpect: Boolean,
    override val containerSource: DeserializedContainerSource?,
) : IrProperty(),
    PersistentIrDeclarationBase<PropertyCarrier>,
    PropertyCarrier {

    override var lastModified: Int = stageController.currentStage
    override var loweredUpTo: Int = stageController.currentStage
    override var values: Array<Carrier>? = null
    override val createdOn: Int = stageController.currentStage

    override var parentField: IrDeclarationParent? = null
    override var originField: IrDeclarationOrigin = origin
    override var removedOn: Int = Int.MAX_VALUE
    override var annotationsField: List<IrConstructorCall> = emptyList()

    override var backingFieldField: IrField? = null

    override var backingField: IrField?
        get() = getCarrier().backingFieldField
        set(v) {
            if (backingField !== v) {
                setCarrier().backingFieldField = v
            }
        }

    override var getterField: IrSimpleFunction? = null

    override var getter: IrSimpleFunction?
        get() = getCarrier().getterField
        set(v) {
            if (getter !== v) {
                setCarrier().getterField = v
            }
        }

    override var setterField: IrSimpleFunction? = null

    override var setter: IrSimpleFunction?
        get() = getCarrier().setterField
        set(v) {
            if (setter !== v) {
                setCarrier().setterField = v
            }
        }

    override var metadataField: MetadataSource? = null

    override var metadata: MetadataSource?
        get() = getCarrier().metadataField
        set(v) {
            if (metadata !== v) {
                setCarrier().metadataField = v
            }
        }

    @Suppress("LeakingThis")
    override var attributeOwnerIdField: IrAttributeContainer = this

    override var attributeOwnerId: IrAttributeContainer
        get() = getCarrier().attributeOwnerIdField
        set(v) {
            if (attributeOwnerId !== v) {
                setCarrier().attributeOwnerIdField = v
            }
        }
}
