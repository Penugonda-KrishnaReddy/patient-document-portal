const API = "http://localhost:9090/documents";

// ---------------- UPLOAD ----------------
async function upload() {
    const file = document.getElementById("fileInput").files[0];

    if (!file) return showMessage(" Please select a PDF file", "red");
    if (!file.name.endsWith(".pdf")) return showMessage(" Only PDF allowed", "red");

    const form = new FormData();
    form.append("file", file);

    try {
        const res = await fetch(API + "/upload", { method: "POST", body: form });

        if (res.ok) {
            showMessage(" File Uploaded Successfully", "green");
        } else {
            showMessage(" Upload Failed", "red");
        }
    } catch (error) {
        showMessage(" Server Error during upload", "red");
    }
}

// ---------------- SHOW ALL DOCUMENTS ----------------
async function loadAll() {
    try {
        const response = await fetch(API);
        if (!response.ok) return showMessage("⚠ Failed to fetch documents", "red");

        const docs = await response.json();
        const tbody = document.getElementById("tableData");
        const table = document.getElementById("docTable");

        tbody.innerHTML = "";
        table.style.display = "table";

        if (docs.length === 0) {
            showMessage(" No documents found", "blue");
            return;
        }

        docs.forEach(d => {
            tbody.innerHTML += `
            <tr id="row-${d.id}">
                <td>${d.id}</td>
                <td>${d.originalFilename}</td>
                <td><a href="${API}/${d.id}" target="_blank">View</a></td>
                <td><button onclick="deleteFromTable(${d.id})" style="background:red;color:white;padding:6px;border:none;">Delete</button></td>
            </tr>`;
        });

        showMessage(" Documents loaded", "blue");
    } catch (error) {
        showMessage(" Server Error fetching documents", "red");
    }
}

// ---------------- VIEW DOCUMENT BY ID ----------------
function viewDoc() {
    const id = document.getElementById("docId").value;
    if (!id) return showMessage("⚠ Enter ID", "red");

    window.open(`${API}/${id}`, "_blank");
    showMessage(" Opening document...", "blue");
}

// ---------------- DELETE BY ID FROM INPUT ----------------
async function deleteDoc() {
    const id = document.getElementById("docId").value;
    if (!id) return showMessage(" Enter ID", "red");

    try {
        const res = await fetch(`${API}/${id}`, { method: "DELETE" });
        const msg = await res.text();

        if (res.status === 404) {
            alert(`❗ Document ID ${id} not found in database`);
            return;
        }

        alert(" Document deleted successfully");
        showMessage(" Deleted Successfully", "green");

        //  Do NOT auto-show table
    } catch (error) {
        showMessage(" Server error during deletion", "red");
    }
}

// ---------------- DELETE DOCUMENT FROM TABLE ----------------
async function deleteFromTable(id) {
    try {
        const res = await fetch(`${API}/${id}`, { method: "DELETE" });
        const msg = await res.text();

        if (res.status === 404) {
            alert(`❗ Document ID ${id} not found in database`);
            return;
        }

        alert(" Document deleted successfully");
        // Remove the row from table
        const row = document.getElementById(`row-${id}`);
        if (row) row.remove();

        showMessage(" Document removed from table", "green");

    } catch (error) {
        showMessage(" Server error during deletion", "red");
    }
}

// ---------------- MESSAGE DISPLAY ----------------
function showMessage(msg, color) {
    const m = document.getElementById("message");
    m.style.color = color;
    m.innerText = msg;
}