let currentPage = 0;
const pageSize = 10;

document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("write").addEventListener("click", function() {
        location.href = 'board_write.html';
    });

    document.getElementById("search").addEventListener("click", function() {
        alert('곧 구현할거다 ㅋ');
        currentPage = 0; // 검색할 때 페이지를 초기화
        fetchData();
    });

    document.getElementById("prev").addEventListener("click", function() {
        if (currentPage > 0) {
            currentPage--;
            fetchData();
        }
    });

    document.getElementById("next").addEventListener("click", function() {
        currentPage++;
        fetchData();
    });

    fetchData();

    function fetchData() {
        fetch(`/posts?page=${currentPage}&size=${pageSize}`)
            .then(response => response.json())
            .then(data => {
                updateTable(data.content);
                updatePagination(data);
            })
            .catch(error => {
                console.error("Error fetching data:", error);
            });
    }

    function updateTable(posts) {
        const tbody = document.querySelector("table tbody");
        tbody.innerHTML = ""; // Clear existing rows

        if (posts.length === 0) {
            const row = document.createElement("tr");
            const cell = document.createElement("td");
            cell.colSpan = 4;
            cell.innerHTML = '<div class="noContents">아직 작성된 글이 없습니다.</div>';
            row.appendChild(cell);
            tbody.appendChild(row);
        } else {
            posts.forEach(post => {
                const row = document.createElement("tr");

                const idCell = document.createElement("td");
                idCell.textContent = post.id;
                row.appendChild(idCell);

                const titleCell = document.createElement("td");
                const titleLink = document.createElement("a");
                titleLink.href = `board_view.html?id=${post.id}`;
                titleLink.textContent = post.title;
                titleCell.appendChild(titleLink);
                row.appendChild(titleCell);

                const authorCell = document.createElement("td");
                authorCell.textContent = post.author;
                row.appendChild(authorCell);

                const dateCell = document.createElement("td");
                dateCell.textContent = post.createdAt;
                row.appendChild(dateCell);

                tbody.appendChild(row);
            });
        }
    }

    function updatePagination(data) {
        const paginationContainer = document.querySelector(".pagination");
        paginationContainer.innerHTML = "";

        const prevButton = document.createElement("button");
        prevButton.id = "prev";
        prevButton.textContent = "이전";
        prevButton.disabled = data.first;
        prevButton.addEventListener("click", function() {
            if (currentPage > 0) {
                currentPage--;
                fetchData();
            }
        });
        paginationContainer.appendChild(prevButton);

        for (let i = 0; i < data.totalPages; i++) {
            const pageButton = document.createElement("span");
            pageButton.textContent = i + 1;
            if (i === data.number) {
                pageButton.classList.add("active");
            }
            pageButton.addEventListener("click", function() {
                currentPage = i;
                fetchData();
            });
            paginationContainer.appendChild(pageButton);
        }

        const nextButton = document.createElement("button");
        nextButton.id = "next";
        nextButton.textContent = "다음";
        nextButton.disabled = data.last;
        nextButton.addEventListener("click", function() {
            currentPage++;
            fetchData();
        });
        paginationContainer.appendChild(nextButton);
    }
});
