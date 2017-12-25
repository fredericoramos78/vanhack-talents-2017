package model

trait AnyModel {
    type T <: AnyModel

    def inserted(id: Long): T

    def isInsertable: Boolean
    def isUpdatable: Boolean
}
