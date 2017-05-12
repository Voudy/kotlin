public class RBNode<K : Comparable<K>, V>(override var key: K, override var value: V?, var color: Boolean): Node<K, V> {

    var left: RBNode<K, V>? = null
    var right: RBNode<K, V>? = null
    var parent: RBNode<K, V>? = null
    override var size: Int = 1

    public fun getMinimum(): RBNode<K, V>? {
        if (this.left == null)
            return this
        else
            return this.left!!.getMinimum()
    }

    public fun getMaximum(): RBNode<K, V>? {
        if (this.right == null)
            return this
        else
            return this.right!!.getMaximum()
    }

}