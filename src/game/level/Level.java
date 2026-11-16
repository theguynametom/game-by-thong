package game.level;

import game.object.Block;
import game.object.Door;
import game.object.GameObject;
import game.object.Goal;
import game.object.Player;
import game.object.Spike;
import game.object.Switch;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Doc file level, tao cac object va cung cap trang thai chung cho man choi. */
public class Level {
    // Kich thuoc nay dung de chuyen toa do theo tile trong file sang pixel tren man hinh.
    public static final int TILE_SIZE = 32;

    // Kich thuoc toan map theo pixel, duoc tinh tu so cot/hang va TILE_SIZE.
    private final int width;
    private final int height;

    // Player duoc tao tu lenh SPAWN va duoc GamePanel cap nhat trong qua trinh choi.
    private final Player player;

    // Cac list rieng giup collision truy cap nhanh nhom object co cung vai tro.
    private final List<Block> blocks;
    private final List<Door> doors;
    private final List<Switch> switches;

    // Danh sach tong hop de GamePanel ve va Collision kiem tra tuong tac cham.
    private final List<GameObject> objects;

    // Co nay dung de dung cap nhat va hien thong bao khi cham Goal.
    private boolean completed;

    /** Luu kich thuoc va cac object da duoc parser tao tu file level. */
    private Level(int width, int height, Player player, List<Block> blocks, List<Door> doors,
                  List<Switch> switches, List<GameObject> objects) {
        this.width = width;
        this.height = height;
        this.player = player;
        this.blocks = blocks;
        this.doors = doors;
        this.switches = switches;
        this.objects = objects;
    }

    /** Tai level tu file van ban; toa do trong file duoc tinh theo tile. */
    public static Level load(Path path) throws IOException {
        // Doc tung dong de parser co the bao loi kem so dong neu du lieu khong hop le.
        List<String> lines = Files.readAllLines(path);

        // Gia tri -1 danh dau cac thong tin kich thuoc/spawn chua duoc khai bao.
        int columns = -1;
        int rows = -1;
        int spawnX = -1;
        int spawnY = -1;

        // Cac co nay kiem tra level co dung mot spawn va it nhat mot goal hay khong.
        boolean hasSpawn = false;
        boolean hasGoal = false;

        // Tao danh sach rong de gom cac object khi doc cac lenh trong file.
        List<Block> blocks = new ArrayList<>();
        List<Door> doors = new ArrayList<>();
        List<Switch> switches = new ArrayList<>();
        List<GameObject> objects = new ArrayList<>();

        // Tam luu khai bao Switch vi Door co the nam sau Switch trong file.
        List<String[]> switchDefinitions = new ArrayList<>();

        // Map tra cuu Door theo ID va Set phat hien ID Switch bi trung.
        Map<String, Door> doorsById = new HashMap<>();
        Set<String> switchIds = new HashSet<>();

        // Duyet theo thu tu file de giu duoc so dong chinh xac cho thong bao loi.
        for (int index = 0; index < lines.size(); index++) {
            // Xoa khoang trang o hai dau de xu ly lenh va comment nhat quan.
            String line = lines.get(index).trim();
            // Dong trong va dong bat dau bang # chi la ghi chu, khong phai lenh level.
            if (line.isEmpty() || line.startsWith("#")) continue;

            // Tach lenh va cac tham so theo mot hoac nhieu khoang trang.
            String[] values = line.split("\\s+");
            try {
                // Chon cach tao object dua tren tu khoa dau tien cua dong.
                switch (values[0]) {
                    case "SIZE" -> {
                        // SIZE nhan so cot va hang; ca hai gia tri phai lon hon 0.
                        requireCount(values, 3, index);
                        columns = positiveInt(values[1], index);
                        rows = positiveInt(values[2], index);
                    }
                    case "SPAWN" -> {
                        // SPAWN chi duoc khai bao sau SIZE va chi xuat hien mot lan.
                        requireCount(values, 3, index);
                        requireSize(columns, rows, index);
                        if (hasSpawn) throw invalidLine(index, "Chi cho phep mot vi tri SPAWN");
                        // Luu toa do theo tile; doi sang pixel khi tao Player o cuoi ham.
                        spawnX = coordinate(values[1], columns, index);
                        spawnY = coordinate(values[2], rows, index);
                        hasSpawn = true;
                    }
                    case "BLOCK_ROW" -> {
                        // Lenh nay tao mot day Block lien tiep tren cung mot hang.
                        requireCount(values, 4, index);
                        requireSize(columns, rows, index);
                        int startX = coordinate(values[1], columns, index);
                        int y = coordinate(values[2], rows, index);
                        int count = positiveInt(values[3], index);
                        if (startX + count > columns) throw invalidLine(index, "BLOCK_ROW vuot qua chieu rong level");
                        // Moi Block co toa do pixel rieng va duoc them vao ca hai danh sach.
                        for (int x = startX; x < startX + count; x++) {
                            Block block = new Block(x * TILE_SIZE, y * TILE_SIZE);
                            blocks.add(block);
                            objects.add(block);
                        }
                    }
                    case "SPIKE" -> {
                        // SPIKE co the tao mot gai hoac mot day gai lien tiep theo chieu ngang.
                        if (values.length != 3 && values.length != 4) {
                            throw invalidLine(index, "SPIKE can toa do X, Y va tuy chon so tile");
                        }
                        requireSize(columns, rows, index);
                        int x = coordinate(values[1], columns, index);
                        int y = coordinate(values[2], rows, index);
                        int count = values.length == 4 ? positiveInt(values[3], index) : 1;
                        if (count > columns - x) throw invalidLine(index, "SPIKE vuot qua chieu rong level");
                        for (int spikeX = x; spikeX < x + count; spikeX++) {
                            objects.add(new Spike(spikeX * TILE_SIZE, y * TILE_SIZE));
                        }
                    }
                    case "DOOR" -> {
                        // DOOR can ID de Switch co the lien ket den dung cua.
                        requireCount(values, 4, index);
                        requireSize(columns, rows, index);
                        String id = values[1];
                        int x = coordinate(values[2], columns, index);
                        int y = coordinate(values[3], rows, index);
                        if (y + 2 > rows) throw invalidLine(index, "DOOR 1x2 tile vuot qua bien level");
                        if (doorsById.containsKey(id)) throw invalidLine(index, "ID DOOR bi trung: " + id);
                        // Cua cao hai tile; them vao map ID, danh sach cua va danh sach ve.
                        Door door = new Door(id, x * TILE_SIZE, y * TILE_SIZE);
                        doorsById.put(id, door);
                        doors.add(door);
                        objects.add(door);
                    }
                    case "SWITCH" -> {
                        // Luu ID Switch, ID Door dich va toa do de xu ly sau khi doc xong file.
                        requireCount(values, 5, index);
                        requireSize(columns, rows, index);
                        coordinate(values[3], columns, index);
                        coordinate(values[4], rows, index);
                        switchDefinitions.add(new String[]{Integer.toString(index), values[1], values[2], values[3], values[4]});
                    }
                    case "GOAL" -> {
                        // GOAL co kich thuoc hai tile moi chieu va chi duoc khai bao mot lan.
                        requireCount(values, 3, index);
                        requireSize(columns, rows, index);
                        if (hasGoal) throw invalidLine(index, "Chi cho phep mot GOAL");
                        int x = coordinate(values[1], columns, index);
                        int y = coordinate(values[2], rows, index);
                        if (x + 2 > columns || y + 2 > rows) {
                            throw invalidLine(index, "GOAL 2x2 tile vuot qua bien level");
                        }
                        objects.add(new Goal(x * TILE_SIZE, y * TILE_SIZE));
                        hasGoal = true;
                    }
                    // Bao loi neu tu khoa khong thuoc dinh dang level hien tai.
                    default -> throw invalidLine(index, "Lenh khong hop le: " + values[0]);
                }
            } catch (NumberFormatException exception) {
                // Chuyen loi parse so thanh thong bao co so dong de de sua file level.
                throw invalidLine(index, "Gia tri toa do/kich thuoc khong phai so nguyen");
            }
        }

        // Tao Switch sau khi da doc het Door, do do cho phep khai bao theo bat ky thu tu nao.
        for (String[] definition : switchDefinitions) {
            int line = Integer.parseInt(definition[0]);
            String switchId = definition[1];
            Door door = doorsById.get(definition[2]);
            if (switchIds.contains(switchId)) throw invalidLine(line, "ID SWITCH bi trung: " + switchId);
            if (door == null) throw invalidLine(line, "Khong tim thay DOOR: " + definition[2]);
            // Kiem tra lai toa do va tao Switch voi tham chieu truc tiep den Door dich.
            int x = coordinate(definition[3], columns, line);
            int y = coordinate(definition[4], rows, line);
            Switch switchObject = new Switch(switchId, x * TILE_SIZE, y * TILE_SIZE, door);
            switchIds.add(switchId);
            switches.add(switchObject);
            objects.add(switchObject);
        }

        // Tu choi level thieu du lieu can thiet thay vi tao Player tai toa do mac dinh.
        if (columns <= 0 || rows <= 0) throw new IOException("Level thieu dong SIZE");
        if (!hasSpawn) throw new IOException("Level thieu vi tri SPAWN");
        if (!hasGoal) throw new IOException("Level thieu GOAL");
        // Doi kich thuoc map va toa do spawn tu tile sang pixel truoc khi tao Level.
        // Danh sach chi doc ngan viec class khac vo tinh them/xoa object sau khi parse.
        return new Level(columns * TILE_SIZE, rows * TILE_SIZE,
                new Player(spawnX * TILE_SIZE, spawnY * TILE_SIZE),
                Collections.unmodifiableList(blocks), Collections.unmodifiableList(doors),
                Collections.unmodifiableList(switches), Collections.unmodifiableList(objects));
    }

    /** Dam bao dong lenh co dung so tham so theo quy uoc cua tung tu khoa. */
    private static void requireCount(String[] values, int expected, int line) throws IOException {
        if (values.length != expected) throw invalidLine(line, "So tham so khong hop le");
    }

    /** Dam bao SIZE da duoc doc truoc khi xu ly lenh can gioi han map. */
    private static void requireSize(int columns, int rows, int line) throws IOException {
        if (columns <= 0 || rows <= 0) throw invalidLine(line, "Can khai bao SIZE truoc lenh nay");
    }

    /** Chuyen chuoi thanh so nguyen duong dung cho kich thuoc map hoac so Block. */
    private static int positiveInt(String value, int line) throws IOException {
        int number = Integer.parseInt(value);
        if (number <= 0) throw invalidLine(line, "Kich thuoc phai lon hon 0");
        return number;
    }

    /** Chuyen chuoi toa do va tu choi gia tri nam ngoai map. */
    private static int coordinate(String value, int limit, int line) throws IOException {
        int coordinate = Integer.parseInt(value);
        if (coordinate < 0 || coordinate >= limit) throw invalidLine(line, "Toa do nam ngoai level");
        return coordinate;
    }

    /** Tao IOException kem so dong de nguoi tao level tim duoc du lieu sai. */
    private static IOException invalidLine(int zeroBasedLine, String message) {
        return new IOException("Level line " + (zeroBasedLine + 1) + ": " + message);
    }

    /** Tra ve chieu rong map theo pixel cho Camera va gioi han di chuyen. */
    public int getWidth() { return width; }

    /** Tra ve chieu cao map theo pixel cho Camera va kiem tra roi khoi map. */
    public int getHeight() { return height; }

    /** Tra ve Player duy nhat cua level. */
    public Player getPlayer() { return player; }

    /** Tra ve cac Block dung lam vat can trong Collision. */
    public List<Block> getBlocks() { return blocks; }

    /** Tra ve cac Door de Collision chi chan nhung cua dang dong. */
    public List<Door> getDoors() { return doors; }

    /** Tra ve cac Switch co the nhan yeu cau tuong tac. */
    public List<Switch> getSwitches() { return switches; }

    /** Tra ve toan bo object can duoc ve hoac kiem tra cham. */
    public List<GameObject> getObjects() { return objects; }

    /** Cho biet Player da cham Goal va hoan thanh level hay chua. */
    public boolean isCompleted() { return completed; }

    /** Danh dau level hoan thanh de GamePanel dung cap nhat gameplay. */
    public void completeLevel() { completed = true; }

    /** Kich hoat Switch gan nhat trong tam va tra ve ket qua co tuong tac duoc hay khong. */
    public boolean interactWithNearbySwitch(Player player) {
        // Duyet cac Switch va chi kich hoat mot cai de mot lan bam E khong dao nhieu cua.
        for (Switch switchObject : switches) {
            if (switchObject.isInRange(player)) {
                switchObject.onInteract(player, this);
                return true;
            }
        }
        return false;
    }
}
