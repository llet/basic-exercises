什么是数据结构？

> 数据元素以及元素之间关系的集合

### 遍历一个二叉树?

```java
import java.util.ArrayList;

public class App {
	private ArrayList<Node> list = new ArrayList<Node>();

	public static void main(String[] args) {
		new App();
	}
	
	public App() {
		Node root = new Node("root");
		Node node2 = new Node("node2");
		Node node3 = new Node("node3");
		Node node4 = new Node("node4");
		Node node5 = new Node("node5");
		Node node6 = new Node("node6");
		Node node7 = new Node("node7");
		root.left = node2;
		node2.right = node3;
		root.right = node4;
		node4.left = node5;
		node4.right = node6;
		node5.right = node7;

		recursive(root);
		for (Node node : list) {
			System.out.println(node.name);
		}
	}

	private void recursive(Node node) {
		if(node!=null) {
			recursive(node.left);
			this.list.add(node);
			recursive(node.right);
		}
	}

	class Node {
		String name;
		Node left;
		Node right;
		public Node(String name) {
			this.name = name;
		}
	}
}
```



树 

二叉树：满二叉树、完全二叉树、最优二叉树(赫夫曼树  )

线性表

线性链表

栈

队列

串

数组

广义表



前序遍历、中序遍历、后续遍历