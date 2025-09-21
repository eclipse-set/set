let matches = []
let currentIndex = -1

document.addEventListener("keydown", (ev) => {
    if (ev.ctrlKey && ev.key === "f") {
        document.body.appendChild(createSearchFeld())
    }

    if (ev.key.toLowerCase() === "enter" && document.activeElement.parentElement === searchFeld) {
        if (ev.shiftKey) {
            toPrevious()
        } else {
            toNext()
        }
    }
})
function createSearchFeld() {
    const searchFeld = document.createElement("div")
    searchFeld.id = "searchFeld"
    searchFeld.textContent = "\u{1F50D}"
    const input = document.createElement("input")
    input.type = "text"
    input.oninput = (ev) => {
        onSearch(input.value)
    }

    searchFeld.appendChild(input)

    const counter = document.createElement("span")
    counter.className = "counter"
    counter.id = "matchCounter"
    counter.textContent = "0 / 0"
    searchFeld.appendChild(counter)

    const previous = document.createElement("button")
    previous.onclick = (ev) => toPrevious()
    previous.textContent = "\u2191"
    searchFeld.appendChild(previous)

    const next = document.createElement("button")
    next.onclick = (ev) => toNext()
    next.textContent = "\u2193"
    searchFeld.appendChild(next)

    const closeButton = document.createElement("button")
    closeButton.onclick = (ev) => close()
    closeButton.textContent = "X"
    searchFeld.appendChild(closeButton)
    return searchFeld
}

function onSearch(value) {
    clear()
    if (!value || value.length < 2) {
        updateCounter();
        return;
    }
    for (let content of document.getElementsByClassName("page")) {
        const walker = document.createTreeWalker(content, NodeFilter.SHOW_TEXT)
        while (walker.nextNode()) {
            const node = walker.currentNode;
            const text = node.textContent;
            let index = text.toLowerCase().indexOf(value.toLowerCase())
            if (index !== -1) {
                const before = document.createTextNode(text.slice(0, index))
                const match = document.createElement('span');
                match.className = "searchHightlight";
                match.textContent = text.slice(index, index + value.length);
                const after = document.createTextNode(text.slice(index + value.length))
                const parent = node.parentNode
                parent.replaceChild(after, node)
                parent.insertBefore(match, after)
                parent.insertBefore(before, match)
                matches.push(match)
            }
        }
    }
    if (matches.length > 0) {
        currentIndex = 0;
        updateActiveMatch();
    }
    updateCounter();

}

function toPrevious() {
    changeActive(-1)
}

function toNext() {
    changeActive(1)
}

function changeActive(value) {
    if (matches.length == 0) {
        return;
    }

    currentIndex += value;
    if (currentIndex < 0) {
        currentIndex = matches.length - 1;
    }

    if (currentIndex >= matches.length) {
        currentIndex = 0;
    }
    updateActiveMatch();
}

function updateCounter() {
    const counter = document.getElementById("matchCounter");
    if (matches.length == 0) {
        counter.textContent = "0 / 0";
    } else {
        counter.textContent = `${currentIndex + 1} / ${matches.length}`
    }
}

function updateActiveMatch() {
    matches.forEach(match =>
        match.classList.remove("active")
    );
    if (matches[currentIndex]) {
        matches[currentIndex].classList.add('active');
        matches[currentIndex].scrollIntoView({
            behavior: "smooth", block: "center"
        });
    }
    updateCounter()
}

function clear() {
    matches.forEach(el => {
        const parent = el.parentNode
        const textNode = document.createTextNode(el.textContent, el)
        parent.replaceChild(textNode, el)
        parent.normalize()
    })
    matches = []
    currentIndex = -1
}

function close() {
    clear()
    document.body.removeChild(searchFeld)
}

