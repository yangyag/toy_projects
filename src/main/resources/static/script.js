document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("postForm").addEventListener("submit", function(event) {
        event.preventDefault();

        const title = document.getElementById("title").value;
        const contents = document.getElementById("contents").value;
        const author = document.getElementById("author").value;

        fetch("/posts", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                title: title,
                contents: contents,
                author: author
            })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.text().then(text => text ? JSON.parse(text) : {});  // 응답이 비어있지 않으면 JSON 파싱
            })
            .then(data => {
                console.log("Success:", data);
                alert("글이 성공적으로 작성되었습니다!");
                location.href = 'board_list.html';
            })
            .catch(error => {
                console.error("Error:", error);
                alert("글 작성 중 오류가 발생했습니다.");
            });
    });

    document.getElementById("listButton").addEventListener("click", function() {
        window.location.href = "board_list.html";
    });

    document.getElementById("helpButton").addEventListener("click", function() {
        const selectedModel = document.querySelector('input[name="model"]:checked').value;
        const prompt = document.getElementById("title").value;

        if (!prompt) {
            alert("제목을 입력해야 합니다.");
            return;
        }

        // 로딩 애니메이션과 버튼 비활성화
        document.querySelector(".overlay").classList.add("active");
        document.getElementById("helpButton").disabled = true;
        document.getElementById("listButton").disabled = true;
        document.querySelector('button[type="submit"]').disabled = true;

        fetch("/gpt/question", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                model: selectedModel,
                prompt: prompt + ' 라는 내용의 주제로 글을 작성하려고 해. 도움을 줘.'
            })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.text().then(text => {
                    try {
                        return JSON.parse(text);
                    } catch (error) {
                        return text;
                    }
                });
            })
            .then(data => {
                // 로딩 애니메이션 숨기기와 버튼 활성화
                document.querySelector(".overlay").classList.remove("active");
                document.getElementById("helpButton").disabled = false;
                document.getElementById("listButton").disabled = false;
                document.querySelector('button[type="submit"]').disabled = false;

                if (typeof data === 'string') {
                    document.getElementById("contents").value = data;
                } else {
                    document.getElementById("contents").value = data.response;
                }
            })
            .catch(error => {
                // 로딩 애니메이션 숨기기와 버튼 활성화
                document.querySelector(".overlay").classList.remove("active");
                document.getElementById("helpButton").disabled = false;
                document.getElementById("listButton").disabled = false;
                document.querySelector('button[type="submit"]').disabled = false;

                console.error("Error:", error);
                alert("도움받기 중 오류가 발생했습니다.");
            });
    });
});
