import org.junit.Test
import org.junit.Assert.*
import AVLSet.AVLSet;
import  java.util.*;

class BinaryTreeTest {

    @Test
    fun testAddRemove() {
        val list = listOf(1,3,4,5,6,7,8,9)
        val original = java.util.TreeSet<Int>()
        val avl = AVLSet<Int>()
        original.addAll(list)
        avl.addAll(list)
        assertEquals(avl, original)
        original.removeAll(listOf(2,6,7))
        avl.removeAll(listOf(2,6,7))
        assertEquals(avl, original)
    }

    @Test
    fun thatTest() {
        val list1 = listOf(1,3,4,5,6,7,8,9);
        val list2 = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9");
        val avl1 = AVLSet<String>()
        val avl2 = AVLSet<Int>()
        avl1.addAll(list2)
        avl2.addAll(list1)
        println(avl1==avl2)
    }

    @Test
    fun subSet() {
        val list = listOf(1,3,4,5,6,7,8,9)
        val original = TreeSet<Int>()
        val avl = AVLSet<Int>()
        assertEquals(original.subSet(2,7), avl.subSet(2,7))
        assertEquals(original.tailSet(3), avl.tailSet(3))
        assertEquals(original.headSet(10), original.headSet(10))
        original.addAll(list)
        avl.addAll(list)
        assertEquals(original.subSet(2,7), avl.subSet(2,7))
        assertEquals(original.tailSet(3), avl.tailSet(3))
        assertEquals(original.headSet(10), original.headSet(10))
    }

    @Test
    fun retain() {
        val list = listOf(1,3,4,5,6,7,8,9,11,12,13,14,15,20,27)
        val original = java.util.TreeSet<Int>()
        val avl = AVLSet<Int>()
        original.addAll(list)
        avl.addAll(list)
        val list2 = listOf(1,3,7,10,15,17,19,22)
        original.retainAll(list2)
        avl.retainAll(list2)
        assertEquals(original,avl)
    }

    @Test
    fun toArray() {
        val list = listOf(1,3,4,5,6,7,8,9,11,12,13,14,15,20,27)
        val original = java.util.TreeSet<Int>()
        val avl = AVLSet<Int>()
        original.addAll(list)
        avl.addAll(list)
        assertArrayEquals(original.toArray(), avl.toArray())
    }
}