document.addEventListener("DOMContentLoaded", function() {
    function showError(message) {
        console.error("Error:", message);
        alert(message);
        document.querySelector(".overlay").classList.remove("active");
    }

    function convertMarkdownToHtml(markdown) {
        let html = markdown
            .replace(/### (.+)/g, '<h3>$1</h3>') // ### 제목을 <h3>로 변환
            .replace(/#### (.+)/g, '<h4>$1</h4>') // #### 제목을 <h2>로 변환
            .replace(/\*\*(.+?)\*\*/g, '<b>$1</b>'); // **텍스트**를 <b>로 변환

        // 단락을 <p> 태그로 감싸기
        html = html.split('\n\n').map(para => `<p>${para}</p>`).join('\n');
        return html;
    }

    function displayPost(data) {
        document.getElementById("postTitle").textContent = data.title || '제목 없음';

        const contents = data.contents ? convertMarkdownToHtml(data.contents) : '';
        document.getElementById("postContents").innerHTML = contents;

        document.getElementById("postAuthor").textContent = data.author || '작성자 없음';
        document.getElementById("postCreatedAt").textContent = data.formattedCreatedAt || '작성일시 없음';
        document.getElementById("postUpdatedAt").textContent = data.formattedUpdatedAt || '수정일시 없음';
    }

    function loadPost(postId) {
        document.querySelector(".overlay").classList.add("active");

        fetch(`/posts/${postId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                displayPost(data);
                document.querySelector(".overlay").classList.remove("active");
            })
            .catch(error => {
                showError("게시물을 불러오는 중 오류가 발생했습니다.");
            });
    }

    function initialize() {
        document.getElementById("listButton").addEventListener("click", function() {
            window.location.href = "board_list.html";
        });

        const urlParams = new URLSearchParams(window.location.search);
        const postId = urlParams.get('id');
        if (postId) {
            loadPost(postId);
        } else {
            showError("유효한 게시물 ID가 없습니다.");
        }
    }

    initialize();
});
