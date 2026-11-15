# AGENTS.md — Quy tắc làm việc với Codex

## 1. Vai trò của tài liệu này
Đây là hướng dẫn dành cho Codex khi làm việc trong repository game 2D platformer của nhóm. Đọc file này và `PROJECT_SPEC.md` trước khi phân tích hoặc sửa code.

## 2. Bối cảnh dự án đã xác nhận
- Thể loại: game 2D Platformer.
- Ngôn ngữ: Java.
- Game có màn chơi dạng tile/map và dữ liệu màn chơi dự kiến được đọc từ file `.txt`.
- Nhóm đã có phần khởi tạo màn hình và đối tượng game ở mức đang triển khai/đã làm theo trao đổi của nhóm.
- Người dùng phụ trách: `Camera`, `Collision`, `Level` và cả hai nhóm đối tượng `Spike`/`Goal`, `Switch`/`Door`.

Không tự suy diễn thêm cốt truyện, đồ họa, cơ chế chiến đấu, hệ thống máu, checkpoint, số màn, kích thước tile hoặc cách điều khiển nếu chưa thấy trong source code hay được người dùng xác nhận.

## 3. Các thành phần theo sơ đồ/phân công hiện tại
- `Main`: tạo cửa sổ `JFrame` (theo mô tả trong bảng phân công).
- `GamePanel`: khu vực game, vòng lặp khoảng 60 FPS, vẽ và cập nhật game (theo mô tả trong bảng phân công).
- `InputHandler`: xử lý input bàn phím.
- `Camera`: bám theo người chơi.
- `Collision`: xử lý va chạm; phân giải trục X rồi trục Y.
- `Level`: đọc dữ liệu và tạo màn chơi từ file `.txt`.
- `GameObject`: lớp cơ sở/abstract cho đối tượng game.
- `Interactive`: interface cho đối tượng có thể tương tác.
- `Player`, `Block`: đối tượng người chơi và khối địa hình.
- `Spike`, `Goal`: bẫy gây chết và đích qua màn.
- `Switch`, `Door`: công tắc và cửa, dùng cho cơ chế giải đố.

Danh sách trên phản ánh sơ đồ/phân công, không khẳng định các file đã tồn tại hoặc code đã hoàn chỉnh. Luôn kiểm tra repository thực tế trước khi tạo/sửa class.

## 4. Phân công
### Phần bạn cùng nhóm
Theo trao đổi: `Main`, `GamePanel`, `GameObject` đã được làm/đang do bạn cùng nhóm phụ trách. Cần đọc source để xác định trạng thái thực tế.

### Phần người dùng
1. `Camera`: camera theo dõi người chơi và giới hạn trong phạm vi bản đồ.
2. `Collision`: kiểm tra và xử lý va chạm, tách xử lý trục X rồi trục Y.
3. `Level`: đọc file `.txt`, chuyển dữ liệu thành các đối tượng/mảng tile của màn chơi.
4. `Spike`: bẫy gây chết hoặc kích hoạt trạng thái thất bại theo luật game được nhóm xác nhận.
5. `Goal`: đích đến, kích hoạt hoàn thành màn theo luật game được nhóm xác nhận.
6. `Switch`: công tắc có thể tương tác, phát tín hiệu/trạng thái.
7. `Door`: cửa nhận trạng thái từ công tắc và thay đổi khả năng đi qua.

Các hành vi chi tiết (ví dụ chạm Spike chết ngay hay trừ máu; Goal cần điều kiện gì; Switch bật/tắt hay chỉ bật; Door mở vĩnh viễn hay theo trạng thái) chưa được xác nhận. Không tự quyết định thay nhóm.

## 5. Nguyên tắc bắt buộc khi thao tác
1. Đọc `PROJECT_SPEC.md`, xem cây thư mục và đọc code liên quan trước khi chỉnh sửa.
2. Không giả định tên package, đường dẫn, chữ ký hàm hoặc cấu trúc class. Xác minh từ source.
3. Không viết lại `Main`, `GamePanel`, `GameObject` nếu không có lý do kỹ thuật và chưa được yêu cầu.
4. Không tự ý chuyển framework, thêm thư viện, đổi kiến trúc hoặc đổi quy ước đặt tên.
5. Không tạo class trùng tên/trùng trách nhiệm. Nếu thiết kế hiện tại mâu thuẫn tài liệu, báo cáo và đề xuất trước.
6. Giữ thay đổi nhỏ, có thể kiểm tra độc lập; không sửa nhiều hệ thống cùng lúc.
7. Viết code dễ đọc, phù hợp dự án sinh viên; giải thích mục đích class/method và cách tích hợp.
8. Không thêm gameplay chưa được xác nhận.
9. Không xóa hoặc ghi đè dữ liệu màn chơi hiện có.
10. Sau mỗi tác vụ, báo cáo file đã sửa, cách chạy/test, kết quả test và việc còn phụ thuộc thành viên khác.

## 6. Quy trình đầu mỗi phiên
1. Đọc `AGENTS.md` và `PROJECT_SPEC.md`.
2. Liệt kê cấu trúc repository.
3. Đọc các class liên quan và lần theo nơi chúng được gọi.
4. Tóm tắt cách game hiện hoạt động, điểm đã có và điểm còn thiếu.
5. Đề xuất một bước triển khai nhỏ.
6. Chỉ sửa code khi người dùng yêu cầu rõ.

## 7. Thứ tự triển khai đề xuất
Thực hiện theo roadmap trong `PROJECT_SPEC.md`: khảo sát/tích hợp → quy ước level → Level → Collision → Camera → Spike/Goal → Switch/Door → kiểm thử tích hợp. Có thể điều chỉnh thứ tự nếu source hiện tại cho thấy dependency khác, nhưng phải giải thích trước.
