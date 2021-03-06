package BinarySearchTree

import Tree

public class BinarySearchTree<K : Comparable<K>, V>(var root: BSNode<K, V>?): Tree<K, V>, Iterable<BSNode<K, V>>{

    override fun iterator(): Iterator<BSNode<K, V>> = BinarySearchTreeIterator(this)

    public override fun draw(){ //функция рисования дерева
        if (root == null){  //если корень не существует
            println("Дерево еще не создано")
            return
        }
        var queue: MutableList<BSNode<K, V>?> = mutableListOf() //лист для вывода текущего уровня
        queue.add(root)
        var isPrint = true
        var indent = 40 //регулировка кривости 1.0
        while (isPrint){
            isPrint = false
            indent = (2 * indent - 1) / 3   //регулировка кривости 2.0
            val new_queue: MutableList<BSNode<K, V>?> = mutableListOf() //следующий уровень
            for (i in 0..queue.size - 1){
                for (j in 1..indent)    //отступ
                    print(" ")
                if (queue[i] == null){  //если нет вершины, то пропуск
                    print("null")
                    new_queue.add(null) //и добавляем null
                    new_queue.add(null)
                }
                else{
                    isPrint = true
                    queue[i]!!.print()  //вывели значение
                    new_queue.add(queue[i]?.left)   //добавили детей
                    new_queue.add(queue[i]?.right)
                }
            }
            println()
            queue = new_queue
        }
    }

    public override fun add(key: K, value: V) {   //добавление вершины
        if (root == null){  //если корень не существует
            root = BSNode(key, value)
            return
        }
        if (check(key)) {
            println("This key already exists")
            return
        }
        var cur = root
        while (true){
            if (cur == null){
                return
            }
            if (cur.key < key){  //если больше текущего, то идем вправо
                if (cur.right == null){ //если справа пусто, то ставим туда новую вершину
                    cur.right = BSNode(key, value)
                    cur.right!!.parent = cur
                    return
                }
                else{   //иначе переходим в правое поддерево
                    cur = cur.right
                }
            }
            else{    //если меньше или равно, то идем влево
                if (cur.left == null){  //если слева пусто, то ставим туда новую вершину
                    cur.left = BSNode(key, value)
                    cur.left!!.parent = cur
                    return
                }
                else{   //иначе переходим влево
                    cur = cur.left
                }
            }
        }
    }

    public override fun remove(key: K){ //функция удаления элемента
        if (root == null){  //если корня не существует
            return
        }
        if (!check(key))
            return
        var cur = root
        var prev: BSNode<K, V>? = null
        var side = true
        while(true){
            if (cur == null){   //если элемент не найден
                return
            }
            else if (cur.key < key){ //спускаемся вправо
                prev = cur
                cur = cur.right
                side = false
            }
            else if (cur.key > key){ //спускаемся влево
                prev = cur
                cur = cur.left
                side = true
            }
            else{   //нашли нужный элемент
                if (cur.left == null && cur.right == null){ //если оба ребенка null, то у родителя ставим ссылку на null
                    if (prev != null){
                        if (side == true){
                            prev.left = null
                        }
                        else{
                            prev.right = null
                        }
                    }
                    else{   //если нет родителя, то корень, значит все дерево null
                        root = null
                    }
                    return
                }
                else if (cur.left == null || cur.right == null){    //если один из ребенков null, то у родителя заменяем ссылку на существующего,
                    if (cur.left != null){                          //если нет родителя, то ставим новый корень
                        if (prev != null){
                            if (side == true){
                                prev.left = cur.left
                            }
                            else{
                                prev.right = cur.left
                            }
                        }
                        else{
                            root = cur.left
                        }
                    }
                    else{
                        if (prev != null){
                            if (side == true){
                                prev.left = cur.right
                            }
                            else{
                                prev.right = cur.right
                            }
                        }
                        else{
                            root = cur.right
                        }
                    }
                    return
                }
                else{ //если есть оба ребенка, то левое поддерево прикрепляем к самому левому элементу правого поддерева, дальше по 2 случаю
                    val tmp = cur
                    cur = cur.right
                    while (cur?.left != null){
                        cur = cur?.left
                    }
                    cur?.left = tmp.left
                    if (prev != null){
                        if (side == true){
                            prev.left = cur
                        }
                        else{
                            prev.right = cur
                        }
                    }
                    else{
                        root = cur
                    }
                    return
                }
            }
        }
    }

    public fun search(key: K): BSNode<K, V>?{  //функция проверки наличия элемента
        var cur = root
        while (true){
            if (cur == null){   //отсутствует
                return null
            }
            else{
                if (cur.key < key){  //спускаемся вправо
                    cur = cur.right
                }
                else if (cur.key > key){ //спускаемся влево
                    cur = cur.left
                }
                else{   //наличие
                    return cur
                }
            }
        }
    }

    public override fun check(key: K): Boolean = search(key) != null

    @Override
    public fun equals(other: BinarySearchTree<K, V>): Boolean{
        val cur_nodes = this.toMutableList()
        val other_nodes = this.toMutableList()
        if (cur_nodes.size != other_nodes.size)
            return false
        for (i in 0.. cur_nodes.size - 1)
            if (!cur_nodes[i].equals(other_nodes[i]))
                return false
        return true
    }

}