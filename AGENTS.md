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

## 8. Quy tắc đặt tên và ngôn ngữ trong code
- Tên package, class, interface, enum, method và biến phải dùng tiếng Anh, theo quy ước Java hiện hành.
- Không dùng tiếng Việt có dấu hoặc không dấu để đặt tên thành phần trong source code.
- Ưu tiên tên cụ thể, dễ hiểu và nhất quán với các class đang có; không đổi tên API hiện tại ngoài phạm vi task.

## 9. Quy tắc comment và JavaDoc
- Mọi comment và ghi chú trong source Java mới hoặc được cập nhật phải viết bằng tiếng Việt không dấu.
- Có thể giữ thuật ngữ kỹ thuật tiếng Anh phổ biến như `Sprite`, `Animation`, `Camera`, `Collision`, `Update`, `Render` và `Input` khi cần.
- Class mới phải có JavaDoc ngắn mô tả trách nhiệm chính.
- Method có logic quan trọng và đoạn xử lý phức tạp cần comment giải thích mục đích hoặc cách hoạt động; ghi rõ lý do cho điều kiện, công thức hay giá trị đặc biệt khi cần.
- Khi sửa code cũ, cập nhật comment bị sai hoặc lỗi thời; giữ comment cũ còn hữu ích.
- Không viết comment lặp lại nguyên văn câu lệnh/tên biến, không comment từng dòng hiển nhiên và không xóa comment có ích chỉ để rút gọn code.

## 10. Nguyên tắc viết và chỉnh sửa code
- Ưu tiên giải pháp đơn giản, dễ đọc, dễ giải thích và phù hợp với dự án sinh viên năm nhất đang học OOP.
- Tái sử dụng thành phần hiện có khi phù hợp; tránh abstraction, tầng xử lý, dependency và code trùng lặp không cần thiết.
- Không over-engineer gameplay nhỏ, không đổi kiến trúc/framework hoặc thêm tính năng ngoài yêu cầu.
- Trước khi sửa class quan trọng, kiểm tra source, nơi gọi và các phụ thuộc liên quan. Giữ thay đổi gọn trong phạm vi được giao.
- Khi thay đổi logic, cập nhật comment liên quan và kiểm tra các thành phần bị ảnh hưởng.

## 11. Sử dụng công cụ hỗ trợ lập trình

### ECC (Everything Claude Code)
- Dùng skill/workflow ECC khi nó khả dụng và phù hợp với độ phức tạp của task, chẳng hạn phân tích yêu cầu, lập kế hoạch, triển khai theo bước hoặc review.
- Với task nhỏ, không cần chạy toàn bộ workflow ECC.
- Kiểm tra skill hoặc công cụ thực sự có thể truy cập trong phiên hiện tại; không coi việc plugin/skill được cài đặt là bằng chứng nó đã chạy.

### Ponytail
- Khi skill Ponytail khả dụng, áp dụng nguyên tắc đơn giản, tránh trùng lặp và tránh abstraction không cần thiết.
- Không tuyên bố đã chạy kiểm tra/review Ponytail nếu chỉ dựa trên việc skill có trong danh sách cài đặt.

### CodeGraph
- Nếu repository có thư mục `.codegraph/` và CodeGraph dùng được, hãy truy vấn CodeGraph trước grep/find hoặc đọc file để tìm hiểu vị trí code, symbol và quan hệ gọi.
- Nêu file hoặc symbol cần khảo sát trong truy vấn; dùng kết quả để xác định phạm vi ảnh hưởng trước khi sửa.
- Không tự tạo index `.codegraph/` nếu repository chưa có; việc index là quyết định của người dùng.
- Nếu CodeGraph không có index, tool không được kết nối hoặc lệnh không chạy được, hãy báo rõ và dùng cách đọc source/tìm tham chiếu thông thường.
- Không giả vờ đã dùng bất kỳ công cụ nào khi thực tế không sử dụng được.

### Phối hợp công cụ
- Chọn công cụ theo nhu cầu; các công cụ hỗ trợ không thay thế việc hiểu source code.
- Không cài đặt công cụ, plugin, thư viện hoặc dependency mới nếu chưa được người dùng cho phép.
- Không dùng Playwright CLI như một phần bắt buộc của workflow dự án.

## 12. Quy tắc quản lý asset
- Lưu toàn bộ asset đồ họa, âm thanh và font trong `resources/artgame/`; không lưu asset trong `src/`.
- Đặt asset vào thư mục phù hợp với chức năng: `characters/`, `tilesets/`, `objects/`, `backgrounds/`, `ui/`, `effects/`, `audio/` hoặc `fonts/`.
- Với asset nhân vật, dùng `resources/artgame/characters/player/`; các file đã được xác nhận hiện có gồm `SPRITE_PORTRAIT.png` và `SPRITE_SHEET.png`.
- Không tự ý đổi tên, chỉnh sửa, cắt, nén, chuyển đổi hoặc xóa asset gốc nếu chưa được cho phép.
- Không tạo asset giả để thay thế asset chưa có. Nếu không tìm thấy asset, báo cáo rõ và yêu cầu người dùng bổ sung.
- Nếu chưa rõ asset thuộc loại nào, hỏi người dùng trước khi sắp xếp.
- Trước khi tích hợp asset, xác minh định dạng, kích thước, nền trong suốt nếu xác định được, đường dẫn và cách tải phù hợp với cấu trúc project.

## 13. Workflow cho mỗi nhiệm vụ
1. **Đọc yêu cầu:** đọc file này; đọc `PROJECT_SPEC.md` khi liên quan; xác định mục tiêu, phạm vi và điều chưa được xác nhận.
2. **Khảo sát:** kiểm tra Git status, cấu trúc và source liên quan; xác định thay đổi chưa commit để giữ nguyên; dùng CodeGraph nếu khả dụng và phù hợp.
3. **Lập kế hoạch:** với thay đổi lớn hoặc nhiều class, liệt kê file, bước triển khai và rủi ro ngắn gọn. Không cần kế hoạch dài cho task nhỏ.
4. **Thực hiện:** thay đổi tối thiểu trong phạm vi được yêu cầu; giữ nguyên thay đổi không liên quan; tuân thủ quy tắc đặt tên, comment và asset.
5. **Kiểm tra:** thực hiện kiểm tra/biên dịch/test phù hợp với task và khả năng môi trường; kiểm tra phạm vi diff. Không nói đã chạy hoặc đạt kiểm tra nếu chưa thực sự chạy.
6. **Báo cáo:** nêu file đã tạo/sửa, thay đổi chức năng hoặc asset, công cụ thực sự đã dùng, kết quả kiểm tra và vấn đề còn lại.

## 14. Bảo vệ project và phối hợp nhóm
- Không ghi đè hay xóa thay đổi chưa commit của thành viên khác; nếu có xung đột không thể xử lý an toàn, dừng và báo cáo.
- Không sửa Java, gameplay, kiến trúc hoặc giao diện khi task chỉ yêu cầu tài liệu/asset.
- Không xóa file cũ hoặc asset nếu chưa được người dùng cho phép; ngoại lệ chỉ khi yêu cầu cụ thể nêu rõ việc loại bỏ.
- Không tự ý commit hoặc push lên GitHub nếu người dùng chưa yêu cầu.
- Nếu phát hiện vấn đề ngoài phạm vi, ghi nhận để báo cáo thay vì tự mở rộng task.
