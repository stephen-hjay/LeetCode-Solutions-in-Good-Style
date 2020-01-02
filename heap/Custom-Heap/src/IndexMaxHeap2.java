/**
 * 使用了反向查找技术
 */
public class IndexMaxHeap2 {

    private int[] data;

    private int[] indexes;
    private int[] reverses;

    private int size;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int peek() {
        if (isEmpty()) {
            throw new IllegalArgumentException("堆空间为空。");
        }
        return data[1];
    }

    private int capacity;

    public IndexMaxHeap2(int capacity) {
        data = new int[capacity + 1];
        indexes = new int[capacity + 1];
        reverses = new int[capacity + 1];

        size = 0;
        this.capacity = capacity;
    }


    /**
     * 比较的是 data 的值，交换的是 indexes
     *
     * @param k
     */
    private void siftUp(int k) {
        int tempIndex = indexes[k];
        int tempValue = data[indexes[k]];
        while (k > 1) {
            if (data[indexes[k / 2]] < tempValue) {
                indexes[k] = indexes[k / 2];

                reverses[indexes[k / 2]] = k;

                k /= 2;
            } else {
                break;
            }
        }
        indexes[k] = tempIndex;

        reverses[tempIndex] = k;

    }

    public void offer(int index, int item) {
        if (size + 1 > capacity) {
            throw new IllegalArgumentException("堆空间已满。");
        }

        // 转换成内部数组的索引
        index++;

        if (index < 1 || index > capacity || reverses[index] != 0) {
            throw new IllegalArgumentException("用户提供的 index 不合法。");
        }

        data[index] = item;
        indexes[size + 1] = index;
        reverses[index] = size + 1;

        size++;
        siftUp(size);
    }

    private void siftDown(int k) {
        int tempIndex = indexes[k];
        int tempValue = data[indexes[k]];
        // 只要它有孩子，注意，这里的等于号是十分关键的
        while (2 * k <= size) {
            int j = 2 * k;
            if (j + 1 <= size && data[indexes[j + 1]] > data[indexes[j]]) {
                j++;
            }
            if (tempValue >= data[indexes[j]]) {
                break;
            }
            indexes[k] = indexes[j];

            reverses[indexes[j]] = k;

            k = j;
        }
        indexes[k] = tempIndex;

        reverses[tempIndex] = k;
    }

    /**
     * 返回的是索引
     *
     * @return
     */
    public int poll() {
        if (size == 0) {
            throw new IllegalArgumentException("堆为空。");
        }
        int ret = indexes[1];
        swap(indexes, 1, size);
        size--;
        siftDown(1);
        return ret;
    }

    public int peekMaxIndex() {
        if (size == 0) {
            throw new RuntimeException("堆里没有可以取出的元素");
        }
        return indexes[1] - 1;
    }

    public int peekMaxValue() {
        if (size == 0) {
            throw new RuntimeException("堆里没有可以取出的元素");
        }
        return data[indexes[1]];
    }

    public void change(int i, int item) {
        i = i + 1;
        data[i] = item;
        // 找到 index[j] = i，j 表示 data[i] 在堆中的位置
        // 之后 shiftUp(j)，在 shiftDown(j)

        int j = reverses[i];

        // 找到了 j
        siftDown(j);
        siftUp(j);
    }

    private void swap(int[] data, int index1, int index2) {
        int temp = data[index1];
        data[index1] = data[index2];
        data[index2] = temp;
    }
}
