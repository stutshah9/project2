import student.TestCase;

/**
 * QuadTree test class that extends TestCase. Used to test QuadTree methods in a
 * variety of situations to ensure that the QuadTree remains ordered and valid
 * (with a focus on correct splitting and merging of the nodes).
 * 
 * @author Stuti and Jaydon
 * @version 2024-03-16
 */
public class QuadTreeTest extends TestCase {

    private QuadTree tree;

    public void setUp() {
        tree = new QuadTree();
    }


    public void testInsert() {
        Point point = new Point("r1", 10, 10);
        Point point1 = new Point("r2", 15, 15);
        Point point2 = new Point("r3", 7, 7);
        Point point3 = new Point("r4", 20, 25);
        Point point4 = new Point("r4", 20, 12);
        Point point5 = new Point("r5", 6, 7);
        Point point6 = new Point("r12", 108, 136);
        Point point7 = new Point("r14", 120, 117);
        Point point8 = new Point("r15", 120, 117);
        tree.insert(point);
        tree.insert(point1);
        tree.insert(point2);
        tree.insert(point3);
        tree.insert(point4);
        tree.insert(point5);
        tree.insert(point6);
        tree.insert(point7);
        tree.insert(point8);
        tree.dump();
        String string1 = systemOut().getHistory();
        assertFuzzyEquals(string1, "node at 0 0 1024 internal\n"
            + "node at 0 0 512 internal\n" + "node at 0 0 256 internal\n"
            + "node at 0 0 128 internal\n" + "node at 0 0 64 internal\n"
            + "node at 0 0 32 internal\n" + "node at 0 0 16 internal\n"
            + "node at 0 0 8\n" + "r3 7 7\n" + "r5 6 7\n"
            + "node at 8 0 8 empty\n" + "node at 0 8 8 empty\n"
            + "node at 8 8 8\n" + "r1 10 10\n" + "r2 15 15\n"
            + "node at 16 0 16\n" + "r4 20 12\n" + "node at 0 16 16 empty\n"
            + "node at 16 16 16\n" + "r4 20 25\n" + "node at 32 0 32 empty\n"
            + "node at 0 32 32 empty\n" + "node at 32 32 32 empty\n"
            + "node at 64 0 64 empty\n" + "node at 0 64 64 empty\n"
            + "node at 64 64 64\n" + "r14 120 117\n" + "r15 120 117\n"
            + "node at 128 0 128 empty\n" + "node at 0 128 128\n"
            + "r12 108 136\n" + "node at 128 128 128 empty\n"
            + "node at 256 0 256 empty\n" + "node at 0 256 256 empty\n"
            + "node at 256 256 256 empty\n" + "node at 512 0 512 empty\n"
            + "node at 0 512 512 empty\n" + "node at 512 512 512 empty\n"
            + "29 quadtree nodes printed");
    }


    public void testInsertRemove() {

        tree.dump();
        String emptyStart = systemOut().getHistory();

        Point point = new Point("r1", 10, 10);
        Point point1 = new Point("r2", 15, 15);
        Point point2 = new Point("r3", 7, 7);
        Point point3 = new Point("r4", 20, 25);
        Point point4 = new Point("r4", 20, 12);
        Point point5 = new Point("r5", 6, 7);
        Point point6 = new Point("r6", 108, 136);
        Point point7 = new Point("r7", 120, 117);
        Point point8 = new Point("r8", 120, 117);
        Point point9 = new Point("r9", 120, 117);
        tree.insert(point);
        tree.insert(point1);
        tree.insert(point2);
        tree.insert(point3);
        tree.insert(point4);
        tree.insert(point5);
        tree.insert(point6);
        tree.insert(point7);
        tree.insert(point8);
        tree.insert(point9);

        Point badsearch1 = new Point("t1", 1000, 1000);
        Point badsearch2 = new Point("t2", 150, 15);
        Point badsearch3 = new Point("t3", 700, 70);
        Point badsearch4 = new Point("t4", 200, 2);
        Point badsearch5 = new Point("t4", 2, 120);
        Point badsearch6 = new Point("t5", 60, 700);
        Point badsearch7 = new Point("t6", 18, 136);
        Point badsearch8 = new Point("t7", 10, 11);
        Point badsearch9 = new Point("t8", 120, 17);

        assertEquals(null, tree.remove(badsearch1));
        assertEquals(null, tree.remove(badsearch2));
        assertEquals(null, tree.remove(badsearch3));
        assertEquals(null, tree.remove(badsearch4));
        assertEquals(null, tree.remove(badsearch5));
        assertEquals(null, tree.remove(badsearch6));
        assertEquals(null, tree.remove(badsearch7));
        assertEquals(null, tree.remove(badsearch8));
        assertEquals(null, tree.remove(badsearch9));

        Point goodsearch = new Point("", 10, 10);
        Point goodsearch1 = new Point("", 15, 15);
        Point goodsearch2 = new Point("", 7, 7);
        Point goodsearch3 = new Point("", 20, 25);
        Point goodsearch4 = new Point("", 20, 12);
        Point goodsearch5 = new Point("", 6, 7);
        Point goodsearch6 = new Point("", 108, 136);
        Point goodsearch7 = new Point("", 120, 117);

        assertEquals("r1", tree.remove(goodsearch).getName());
        assertEquals("r2", tree.remove(goodsearch1).getName());
        assertEquals("r3", tree.remove(goodsearch2).getName());
        assertEquals("r4", tree.remove(goodsearch3).getName());
        assertEquals("r4", tree.remove(goodsearch4).getName());
        assertEquals("r5", tree.remove(goodsearch5).getName());
        assertEquals("r6", tree.remove(goodsearch6).getName());
        assertEquals("r7", tree.remove(goodsearch7).getName());
        assertEquals("r8", tree.remove(goodsearch7).getName());
        assertEquals("r9", tree.remove(goodsearch7).getName());

        systemOut().clearHistory();
        tree.dump();
        String emptyEnd = systemOut().getHistory();
        assertEquals(emptyEnd, emptyStart);

        tree.insert(point);
        tree.insert(point1);
        tree.insert(point2);
        tree.insert(point3);
        tree.insert(point4);
        assertEquals("r1", tree.remove(goodsearch).getName());
        assertEquals("r2", tree.remove(goodsearch1).getName());
        assertEquals("r3", tree.remove(goodsearch2).getName());
        assertEquals("r4", tree.remove(goodsearch3).getName());
        assertEquals("r4", tree.remove(goodsearch4).getName());

        systemOut().clearHistory();
        tree.dump();
        emptyEnd = systemOut().getHistory();
        assertEquals(emptyEnd, emptyStart);

    }


    public void testInsertRegionSearch() {

        Point point = new Point("r1", 10, 10);
        Point point1 = new Point("r2", 15, 15);
        Point point2 = new Point("r3", 7, 7);
        Point point3 = new Point("r4", 20, 25);
        Point point4 = new Point("r4", 20, 12);
        Point point5 = new Point("r5", 6, 7);
        Point point6 = new Point("r6", 108, 136);
        Point point7 = new Point("r7", 120, 117);
        Point point8 = new Point("r8", 120, 117);
        tree.insert(point);
        tree.insert(point1);
        tree.insert(point2);
        tree.insert(point3);
        tree.insert(point4);
        tree.insert(point5);
        tree.insert(point6);
        tree.insert(point7);
        tree.insert(point8);
        Rectangle region = new Rectangle(10, 10, 15, 15);
        tree.regionSearch(region);
        assertEquals("Point found: (r1, 10, 10)\n"
            + "Point found: (r2, 15, 15)\n" + "Point found: (r4, 20, 12)\n"
            + "Point found: (r4, 20, 25)\n" + "11 quadtree nodes visited\n",
            systemOut().getHistory());
        systemOut().clearHistory();

        Rectangle region1 = new Rectangle(108, 117, 12, 19);
        tree.regionSearch(region1);
        assertEquals("Point found: (r7, 120, 117)\n"
            + "Point found: (r8, 120, 117)\n" + "Point found: (r6, 108, 136)\n"
            + "6 quadtree nodes visited\n", systemOut().getHistory());
        systemOut().clearHistory();
    }

}
