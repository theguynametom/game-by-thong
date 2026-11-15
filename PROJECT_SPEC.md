# PROJECT_SPEC.md — Đặc tả dự án game 2D Platformer

> Tài liệu này tổng hợp thông tin nhóm đã cung cấp. Những chi tiết chưa có bằng chứng được ghi rõ là **Chưa xác nhận**, không xem là yêu cầu chính thức.

## 1. Tổng quan
- **Thể loại:** 2D Platformer.
- **Ngôn ngữ:** Java.
- **Mục tiêu:** xây dựng một game platformer 2D có người chơi di chuyển qua màn chơi, tương tác với địa hình và các đối tượng gameplay.
- **Dữ liệu màn:** dự kiến lưu trong file văn bản `.txt`.
- **Framework/thư viện đồ họa:** Chưa xác nhận. Bảng phân công có nhắc `JFrame`, vì vậy có dấu hiệu dùng Java Swing/AWT; cần kiểm tra import và source code để kết luận.

## 2. Cấu trúc và thành phần dự kiến

| Thành phần | Trách nhiệm theo phân công |
|---|---|
| `Main` | Tạo cửa sổ `JFrame` |
| `GamePanel` | Game loop khoảng 60 FPS, cập nhật và vẽ game |
| `InputHandler` | Nhận input bàn phím |
| `GameObject` | Lớp cơ sở/abstract cho các đối tượng game |
| `Interactive` | Interface cho đối tượng có thể tương tác |
| `Player` | Người chơi |
| `Block` | Khối địa hình/nền |
| `Camera` | Bám theo người chơi |
| `Collision` | Xử lý va chạm, phân giải trục X rồi Y |
| `Level` | Đọc dữ liệu và tạo màn từ file `.txt` |
| `Spike` | Bẫy gây chết |
| `Goal` | Đích/điều kiện qua màn |
| `Switch` | Công tắc tương tác |
| `Door` | Cửa, có thể mở/đóng theo cơ chế công tắc |

Cây thư mục nhìn thấy trong ảnh có thư mục `screen` chứa `GamePanel.class`, `GamePanel.java`, `Main.class`, `Main.java`. Đây chỉ là phần cây thư mục được chụp; chưa đủ để kết luận toàn bộ cấu trúc repository.

## 3. Phân công hiện tại

### Thành viên còn lại
Theo trao đổi trong nhóm:
- `Main`
- `GamePanel`
- `GameObject`

Ảnh trao đổi ghi thành viên này đã làm phần màn hình và `GameObject`. Cần kiểm tra code để biết mức độ hoàn thiện và cách các class được gọi.

### Người dùng
- `Camera` — camera bám theo người chơi.
- `Collision` — va chạm, tách trục X rồi Y.
- `Level` — đọc và chia/tạo một màn từ file `.txt`.
- `Spike` — bẫy gây chết.
- `Goal` — đích qua màn.
- `Switch` — công tắc.
- `Door` — cửa liên kết với cơ chế công tắc.

Người dùng chọn làm **cả hai nhóm đối tượng**: `Spike`/`Goal` và `Switch`/`Door`.

## 4. Trách nhiệm và tiêu chí hoàn thành từng phần

### 4.1 Camera
**Mục tiêu:** hiển thị vùng bản đồ quanh người chơi và cập nhật vị trí nhìn theo người chơi.

Tiêu chí:
- Có thể chuyển tọa độ thế giới (world) sang tọa độ màn hình (screen).
- Theo dõi người chơi theo quy tắc nhóm thống nhất.
- Không hiển thị vượt ra ngoài biên bản đồ nếu bản đồ có kích thước lớn hơn viewport.
- Tương thích với cách `GamePanel` vẽ `Player` và `Block`.
- Kiểm tra được ở vị trí đầu, giữa và cuối bản đồ.

**Chưa xác nhận:** kích thước viewport, kích thước tile, camera luôn bám giữa hay chỉ bám sau một ngưỡng, cách xử lý map nhỏ hơn màn hình.

### 4.2 Collision
**Mục tiêu:** ngăn người chơi xuyên qua địa hình và xử lý va chạm ổn định.

Tiêu chí:
- Xác định vùng va chạm của Player và Block từ source hiện tại.
- Cập nhật vị trí theo trục X rồi xử lý va chạm trục X.
- Cập nhật vị trí theo trục Y rồi xử lý va chạm trục Y.
- Khi va chạm, điều chỉnh vị trí về sát bề mặt vật cản, không để chồng lấn.
- Kiểm tra đi trái/phải, rơi xuống nền, đụng trần và va vào cạnh tường.
- Không tự thêm trọng lực, nhảy hoặc vận tốc nếu phần đó thuộc `Player`/`GamePanel` và chưa thống nhất.

**Chưa xác nhận:** AABB hay kiểu hitbox khác, đơn vị vận tốc, trọng lực, nhảy, xử lý va chạm với Spike/Door.

### 4.3 Level
**Mục tiêu:** đọc file `.txt` và tạo dữ liệu/đối tượng cho một màn chơi.

Tiêu chí:
- Có quy ước ký tự đại diện cho từng loại tile/object.
- Đọc được file level và xử lý lỗi file không tồn tại hoặc dữ liệu không hợp lệ.
- Chuyển dữ liệu thành cấu trúc mà `GamePanel`, `Collision`, `Camera` có thể sử dụng.
- Xác định được vị trí spawn của Player.
- Có thể nạp ít nhất một level mẫu và kiểm tra đúng vị trí Block/đối tượng.

**Chưa xác nhận:** định dạng file, ký tự map, tile size, đường dẫn tài nguyên, có nhiều level hay chỉ một level ở bản demo.

### 4.4 Spike và Goal
**Spike:** có hitbox, được Level tạo từ dữ liệu màn, có thể được Collision hoặc hệ thống gameplay phát hiện khi Player chạm vào.

**Goal:** có vị trí/hitbox, được Level tạo ra, có thể phát hiện Player đến đích và thông báo hoàn thành màn.

**Chưa xác nhận:** chạm Spike là chết ngay hay trừ máu; Goal tự động hoàn thành hay cần điều kiện khác; cách reset/chuyển level.

### 4.5 Switch và Door
**Switch:** có trạng thái và nhận tương tác từ Player thông qua `Interactive` hoặc cơ chế hiện có.

**Door:** có trạng thái đóng/mở; nhận tín hiệu từ Switch. Khi mở, cách Door ảnh hưởng đến Collision phải thống nhất.

**Chưa xác nhận:** tương tác bằng phím nào, Switch bật/tắt hay chỉ bật, Door có tự đóng lại không, một Switch điều khiển một hay nhiều Door.

## 5. Các quyết định kỹ thuật cần xác minh trong source
1. Package thực tế và vị trí source.
2. Dùng Swing/AWT hay thư viện khác.
3. `GamePanel` tổ chức game loop, update và render thế nào.
4. `GameObject` có các thuộc tính/phương thức nào (x, y, width, height, update, draw...).
5. Player lưu vị trí, vận tốc, hitbox và input ra sao.
6. `Block` được lưu riêng lẻ, trong mảng 2D hay collection.
7. `Interactive` quy định method nào.
8. Cách quản lý trạng thái game và chuyển màn.
9. Quy ước tọa độ, tile size, viewport và file `.txt`.

## 6. Roadmap code đề xuất

| Giai đoạn | Công việc | Kết quả cần có |
|---|---|---|
| 0. Khảo sát | Đọc toàn bộ class nền, xác định dependency và cách chạy project | Bản đồ kiến trúc thực tế; danh sách API cần tích hợp |
| 1. Chốt định dạng level | Thống nhất ký tự, tile size, spawn, đường dẫn file | Một file level mẫu và bảng quy ước |
| 2. Level | Đọc `.txt`, tạo Block và các object theo ký tự | Nạp được màn mẫu |
| 3. Collision | Xử lý va chạm Player–Block theo X rồi Y | Player không xuyên nền/tường/trần |
| 4. Camera | Theo dõi Player, giới hạn theo kích thước map | Camera cuộn đúng khi Player di chuyển |
| 5. Spike và Goal | Tạo bẫy và đích, kết nối với Level và trạng thái game | Chạm bẫy/đến đích cho kết quả đúng theo luật nhóm |
| 6. Switch và Door | Tạo tương tác, quản lý trạng thái và liên kết | Switch điều khiển Door, Door phối hợp Collision |
| 7. Tích hợp | Kiểm tra toàn bộ một màn từ spawn đến goal | Một màn chơi hoàn chỉnh, không lỗi tích hợp |
| 8. Hoàn thiện | Kiểm tra biên map, lỗi file, reset và các tình huống va chạm | Bản demo ổn định để trình bày |

### Thứ tự phụ thuộc
- Chốt định dạng map trước khi code `Level`.
- `Level` cần tạo ra `Block`, `Spike`, `Goal`, `Switch`, `Door` theo quy ước đã thống nhất.
- `Collision` cần biết cách lấy hitbox của `Player` và `Block`; Door cần được bổ sung vào kiểm tra va chạm khi đóng.
- `Camera` cần biết vị trí/kích thước Player, kích thước map và viewport.
- `Spike`/`Goal` cần được kiểm tra va chạm hoặc overlap với Player.
- `Switch`/`Door` cần thống nhất API tương tác và trạng thái trước khi nối với nhau.

Có thể thay đổi thứ tự sau giai đoạn khảo sát nếu source hiện tại có dependency khác.

## 7. Cách chia nhỏ task để làm với Codex
Mỗi lần chỉ giao một task:
1. Yêu cầu Codex đọc class liên quan và mô tả cách tích hợp, chưa sửa code.
2. Xác nhận phương án.
3. Yêu cầu triển khai đúng phạm vi một task.
4. Yêu cầu giải thích thay đổi và cách test.
5. Chạy game, ghi nhận lỗi, sửa trước khi sang task tiếp theo.

Không yêu cầu Codex triển khai đồng thời Camera, Collision, Level và cả bốn object trong một prompt.
