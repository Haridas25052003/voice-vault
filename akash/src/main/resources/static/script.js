/**
 * Visionaire v2 — Full Studio Frontend
 * Features: Generate, Gallery, History, Lightbox, Quick Styles, Inspire, Preview Panel
 */

/* ══ DOM ══════════════════════════════════════════════════════ */
const $ = id => document.getElementById(id);

const promptInput     = $('prompt');
const styleSelect     = $('style');
const cameraSelect    = $('camera');
const lightingSelect  = $('lighting');
const colorMoodSelect = $('colorMood');
const negativeInput   = $('negativePrompt');
const generateBtn     = $('generateBtn');
const btnText         = $('btnText');
const errorBanner     = $('errorBanner');
const errorMessage    = $('errorMessage');
const closeError      = $('closeError');
const loadingState    = $('loadingState');
const loadingStep     = $('loadingStep');
const loadingBar      = $('loadingBar');
const resultSection   = $('resultSection');
const generatedImage  = $('generatedImage');
const downloadBtn     = $('downloadBtn');
const copyUrlBtn      = $('copyUrlBtn');
const regenerateBtn   = $('regenerateBtn');
const resultMeta      = $('resultMeta');
const revisedPromptBox  = $('revisedPromptBox');
const revisedPromptText = $('revisedPromptText');
const charCount       = $('charCount');
const charBar         = $('charBar');
const inspireBtn      = $('inspireBtn');
const resolutionBtns  = $('resolutionBtns');
const galleryGrid     = $('galleryGrid');
const galleryCount    = $('galleryCount');
const historyList     = $('historyList');
const clearHistory    = $('clearHistory');
const previewImage    = $('previewImage');
const previewPlaceholder = $('previewPlaceholder');
const previewStats    = $('previewStats');
const statModel       = $('statModel');
const statSize        = $('statSize');
const statTime        = $('statTime');
const lightbox        = $('lightbox');
const lightboxImg     = $('lightboxImg');
const lightboxClose   = $('lightboxClose');
const lightboxOverlay = $('lightboxOverlay');
const panelClose      = $('panelClose');

/* ══ STATE ════════════════════════════════════════════════════ */
const API_ENDPOINT = '/api/generate-image';
let selectedSize   = '1024x1024';
let history        = JSON.parse(localStorage.getItem('visionaire_history') || '[]');
let gallery        = JSON.parse(localStorage.getItem('visionaire_gallery') || '[]');
let activeStyle    = null;
let loadingTimer   = null;
let progressTimer  = null;
let progressVal    = 0;

const INSPIRE_PROMPTS = [
    'A lone astronaut standing on a crimson Martian ridge at golden hour, hyper-realistic, volumetric light rays piercing the dusty atmosphere',
    'An enchanted forest at midnight, bioluminescent mushrooms glowing blue, a fox with starlight fur sitting by a moonlit stream',
    'A baroque-era portrait of a futuristic AI robot painted in the style of Rembrandt, dramatic chiaroscuro lighting',
    'A micro-city inside a snow globe terrarium, tiny glowing buildings, winter storm swirling inside, dark background',
    'Deep ocean trench with ancient sunken cathedral, bioluminescent creatures, shafts of light piercing dark water',
    'A samurai made of origami paper sitting under cherry blossoms, petals falling, soft rain, ink wash style',
    'Clouds shaped like enormous whales floating above a neon-lit Tokyo skyline at dusk',
    'An old lighthouse on a stormy sea cliff, lightning strike, dramatic waves crashing, golden window light',
    'A glass terrarium world with a miniature jungle inside, condensation on glass, macro photography',
    'A Viking warrior woman looking out over a fjord at sunset, intricate armor, braided hair catching wind',
    'Abstract visualization of music as flowing geometric shapes in space, synesthesia art',
    'A cozy witch cottage in autumn woods, smoke from chimney, pumpkins, warm candlelight through windows',
];

const LOADING_STEPS = [
    'Sending prompt to DALL·E 3...',
    'Analyzing composition...',
    'Rendering scene elements...',
    'Applying style & lighting...',
    'Adding fine details...',
    'Upscaling to HD resolution...',
    'Almost ready...',
];

/* ══ BACKGROUND CANVAS ════════════════════════════════════════ */
(function initBg() {
    const canvas = $('bg-canvas');
    const ctx = canvas.getContext('2d');
    const particles = [];

    function resize() {
        canvas.width = window.innerWidth;
        canvas.height = window.innerHeight;
    }

    for (let i = 0; i < 60; i++) {
        particles.push({
            x: Math.random(),
            y: Math.random(),
            r: Math.random() * 1.5 + 0.3,
            dx: (Math.random() - 0.5) * 0.0002,
            dy: (Math.random() - 0.5) * 0.0002,
            o: Math.random() * 0.4 + 0.1,
        });
    }

    function draw() {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        const W = canvas.width, H = canvas.height;

        particles.forEach(p => {
            p.x += p.dx; p.y += p.dy;
            if (p.x < 0) p.x = 1; if (p.x > 1) p.x = 0;
            if (p.y < 0) p.y = 1; if (p.y > 1) p.y = 0;
            ctx.beginPath();
            ctx.arc(p.x * W, p.y * H, p.r, 0, Math.PI * 2);
            ctx.fillStyle = `rgba(108,99,255,${p.o})`;
            ctx.fill();
        });

        requestAnimationFrame(draw);
    }

    resize();
    window.addEventListener('resize', resize);
    draw();
})();

/* ══ TABS ═════════════════════════════════════════════════════ */
document.querySelectorAll('.nav-item').forEach(btn => {
    btn.addEventListener('click', () => {
        const tab = btn.dataset.tab;
        document.querySelectorAll('.nav-item').forEach(b => b.classList.remove('active'));
        document.querySelectorAll('.tab-content').forEach(t => t.classList.remove('active'));
        btn.classList.add('active');
        $('tab-' + tab).classList.add('active');

        if (tab === 'gallery') renderGallery();
        if (tab === 'history') renderHistory();
    });
});

/* ══ CHARACTER COUNTER ════════════════════════════════════════ */
promptInput.addEventListener('input', () => {
    const len = promptInput.value.length;
    charCount.textContent = len;
    charBar.style.width = (len / 4000 * 100) + '%';
    charBar.style.background = len > 3500 ? '#ff453a' : len > 2000 ? '#f5a623' : '#6c63ff';
});

/* ══ RESOLUTION PICKER ════════════════════════════════════════ */
resolutionBtns.querySelectorAll('.res-btn').forEach(btn => {
    btn.addEventListener('click', () => {
        resolutionBtns.querySelectorAll('.res-btn').forEach(b => b.classList.remove('active'));
        btn.classList.add('active');
        selectedSize = btn.dataset.size;
    });
});

/* ══ QUICK STYLES ═════════════════════════════════════════════ */
document.querySelectorAll('.quick-style').forEach(btn => {
    btn.addEventListener('click', () => {
        const style = btn.dataset.style;
        if (activeStyle === style) {
            activeStyle = null;
            btn.classList.remove('active');
            styleSelect.value = '';
        } else {
            document.querySelectorAll('.quick-style').forEach(b => b.classList.remove('active'));
            btn.classList.add('active');
            activeStyle = style;
            styleSelect.value = '';
        }
    });
});

/* ══ INSPIRE ME ═══════════════════════════════════════════════ */
inspireBtn.addEventListener('click', () => {
    const p = INSPIRE_PROMPTS[Math.floor(Math.random() * INSPIRE_PROMPTS.length)];
    promptInput.value = p;
    charCount.textContent = p.length;
    charBar.style.width = (p.length / 4000 * 100) + '%';
    promptInput.focus();

    // Quick flash animation
    promptInput.style.borderColor = '#6c63ff';
    setTimeout(() => { promptInput.style.borderColor = ''; }, 600);
});

/* ══ ERROR ════════════════════════════════════════════════════ */
function showError(msg) {
    errorMessage.textContent = msg;
    errorBanner.hidden = false;
    setState('idle');
}

closeError.addEventListener('click', () => { errorBanner.hidden = true; });

/* ══ STATE MACHINE ════════════════════════════════════════════ */
function setState(state) {
    if (state === 'loading') {
        generateBtn.disabled = true;
        btnText.textContent = 'Generating...';
        errorBanner.hidden = true;
        loadingState.hidden = false;
        resultSection.hidden = true;
        startProgress();
        startSteps();
    } else {
        generateBtn.disabled = false;
        btnText.textContent = 'Generate Image';
        loadingState.hidden = true;
        stopProgress();
        stopSteps();
    }
}

/* ══ LOADING PROGRESS ═════════════════════════════════════════ */
function startProgress() {
    progressVal = 0;
    loadingBar.style.width = '0%';
    progressTimer = setInterval(() => {
        // Simulate progress — slows as it approaches 90%
        const remaining = 90 - progressVal;
        progressVal += remaining * 0.04;
        if (progressVal > 89) progressVal = 89;
        loadingBar.style.width = progressVal + '%';
    }, 300);
}

function stopProgress() {
    clearInterval(progressTimer);
    loadingBar.style.width = '100%';
    setTimeout(() => { loadingBar.style.width = '0%'; }, 600);
}

function startSteps() {
    let i = 0;
    loadingStep.textContent = LOADING_STEPS[0];
    loadingTimer = setInterval(() => {
        i = (i + 1) % LOADING_STEPS.length;
        loadingStep.style.opacity = '0';
        setTimeout(() => {
            loadingStep.textContent = LOADING_STEPS[i];
            loadingStep.style.opacity = '1';
            loadingStep.style.transition = 'opacity 0.3s';
        }, 150);
    }, 2500);
}

function stopSteps() { clearInterval(loadingTimer); }

/* ══ BUILD FINAL PROMPT ═══════════════════════════════════════ */
function buildFinalPrompt(prompt) {
    const parts = [prompt.trim()];

    // Active quick style overrides dropdown
    const style = activeStyle || styleSelect.value;
    if (style) parts.push(style);

    if (lightingSelect.value) parts.push(lightingSelect.value);
    if (cameraSelect.value) parts.push(cameraSelect.value);
    if (colorMoodSelect.value) parts.push(colorMoodSelect.value);

    // Negative prompt appended as instruction
    const neg = negativeInput.value.trim();
    if (neg) parts.push(`Avoid: ${neg}`);

    return parts.join(', ');
}

/* ══ GENERATE IMAGE ═══════════════════════════════════════════ */
async function generateImage() {
    const rawPrompt = promptInput.value.trim();

    if (!rawPrompt) {
        showError('Please enter a prompt to generate an image.');
        promptInput.focus();
        return;
    }
    if (rawPrompt.length < 3) {
        showError('Prompt is too short. Add more detail for better results.');
        return;
    }

    setState('loading');
    loadingState.scrollIntoView({ behavior: 'smooth', block: 'nearest' });

    const finalPrompt = buildFinalPrompt(rawPrompt);
    const body = { prompt: finalPrompt, size: selectedSize };

    try {
        const res = await fetch(API_ENDPOINT, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body),
        });

        const data = await res.json();

        if (!res.ok) {
            const msg = data.fieldErrors?.map(f => f.message).join(', ')
                || data.message
                || `Request failed (${res.status})`;
            showError(msg);
            return;
        }

        displayResult(data, rawPrompt);

    } catch (err) {
        console.error(err);
        showError('Network error. Please check your connection and try again.');
    }
}

/* ══ DISPLAY RESULT ═══════════════════════════════════════════ */
function displayResult(data, rawPrompt) {
    setState('idle');

    // Update main image
    generatedImage.src = data.imageUrl;
    generatedImage.alt = rawPrompt.substring(0, 80);
    generatedImage.onclick = () => openLightbox(data.imageUrl);

    // Download
    downloadBtn.href = data.imageUrl;
    downloadBtn.setAttribute('download', `visionaire-${Date.now()}.png`);

    // Copy URL
    copyUrlBtn.onclick = () => copyText(data.imageUrl, copyUrlBtn);

    // Regenerate
    regenerateBtn.onclick = generateImage;

    // Meta bar
    const model = (data.model || 'dall-e-3').toUpperCase();
    const size  = data.size || selectedSize;
    const time  = new Date().toLocaleTimeString();
    resultMeta.textContent = `${model} · ${size} · HD · ${time}`;

    // Revised prompt
    if (data.revisedPrompt) {
        revisedPromptText.textContent = data.revisedPrompt;
        revisedPromptBox.hidden = false;
    } else {
        revisedPromptBox.hidden = true;
    }

    resultSection.hidden = false;
    resultSection.scrollIntoView({ behavior: 'smooth', block: 'nearest' });

    // Update preview panel
    updatePreview(data, size, model, time);

    // Save to history & gallery
    const entry = {
        id: Date.now(),
        prompt: rawPrompt,
        imageUrl: data.imageUrl,
        size,
        model,
        time,
    };
    addToHistory(entry);
    addToGallery(entry);
}

/* ══ PREVIEW PANEL ════════════════════════════════════════════ */
function updatePreview(data, size, model, time) {
    previewPlaceholder.hidden = true;
    previewImage.src = data.imageUrl;
    previewImage.hidden = false;
    previewImage.onclick = () => openLightbox(data.imageUrl);
    previewStats.hidden = false;
    statModel.textContent = model;
    statSize.textContent = size;
    statTime.textContent = time;
}

panelClose.addEventListener('click', () => {
    document.querySelector('.preview-panel').style.display = 'none';
});

/* ══ GALLERY ══════════════════════════════════════════════════ */
function addToGallery(entry) {
    gallery.unshift(entry);
    if (gallery.length > 50) gallery.pop();
    localStorage.setItem('visionaire_gallery', JSON.stringify(gallery));
    galleryCount.textContent = `${gallery.length} image${gallery.length !== 1 ? 's' : ''}`;
}

function renderGallery() {
    galleryCount.textContent = `${gallery.length} image${gallery.length !== 1 ? 's' : ''}`;
    if (gallery.length === 0) {
        galleryGrid.innerHTML = `
            <div class="empty-state" style="grid-column:1/-1">
                <svg width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5"><rect x="3" y="3" width="18" height="18" rx="2"/><circle cx="8.5" cy="8.5" r="1.5"/><polyline points="21 15 16 10 5 21"/></svg>
                <p>Generate some images first!</p>
            </div>`;
        return;
    }

    galleryGrid.innerHTML = gallery.map(item => `
        <div class="gallery-item" data-url="${item.imageUrl}">
            <img src="${item.imageUrl}" alt="${escapeHtml(item.prompt)}" loading="lazy"/>
            <div class="gallery-item-overlay">
                <div class="gallery-item-prompt">${escapeHtml(item.prompt)}</div>
            </div>
        </div>
    `).join('');

    galleryGrid.querySelectorAll('.gallery-item').forEach(el => {
        el.addEventListener('click', () => openLightbox(el.dataset.url));
    });
}

/* ══ HISTORY ══════════════════════════════════════════════════ */
function addToHistory(entry) {
    history.unshift(entry);
    if (history.length > 100) history.pop();
    localStorage.setItem('visionaire_history', JSON.stringify(history));
}

function renderHistory() {
    if (history.length === 0) {
        historyList.innerHTML = `
            <div class="empty-state">
                <svg width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5"><circle cx="12" cy="12" r="10"/><polyline points="12 6 12 12 16 14"/></svg>
                <p>No history yet.</p>
            </div>`;
        return;
    }

    historyList.innerHTML = history.map((item, idx) => `
        <div class="history-item" data-idx="${idx}">
            <img class="history-thumb" src="${item.imageUrl}" alt=""/>
            <div class="history-info">
                <div class="history-prompt">${escapeHtml(item.prompt)}</div>
                <div class="history-meta">${item.model} · ${item.size} · ${item.time}</div>
            </div>
            <button class="history-reuse" data-prompt="${escapeHtml(item.prompt)}">↩ Reuse</button>
        </div>
    `).join('');

    historyList.querySelectorAll('.history-reuse').forEach(btn => {
        btn.addEventListener('click', e => {
            e.stopPropagation();
            promptInput.value = btn.dataset.prompt;
            charCount.textContent = promptInput.value.length;
            charBar.style.width = (promptInput.value.length / 4000 * 100) + '%';
            // Switch to generate tab
            document.querySelector('[data-tab="generate"]').click();
            promptInput.focus();
        });
    });
}

clearHistory.addEventListener('click', () => {
    if (confirm('Clear all history?')) {
        history = [];
        gallery = [];
        localStorage.removeItem('visionaire_history');
        localStorage.removeItem('visionaire_gallery');
        renderHistory();
    }
});

/* ══ LIGHTBOX ═════════════════════════════════════════════════ */
function openLightbox(url) {
    lightboxImg.src = url;
    lightbox.hidden = false;
    document.body.style.overflow = 'hidden';
}

function closeLightbox() {
    lightbox.hidden = true;
    document.body.style.overflow = '';
}

lightboxClose.addEventListener('click', closeLightbox);
lightboxOverlay.addEventListener('click', closeLightbox);
document.addEventListener('keydown', e => { if (e.key === 'Escape') closeLightbox(); });

/* ══ CLIPBOARD ════════════════════════════════════════════════ */
async function copyText(text, btn) {
    try {
        await navigator.clipboard.writeText(text);
        const orig = btn.textContent;
        btn.textContent = '✓ Copied!';
        btn.style.color = '#30d158';
        setTimeout(() => { btn.textContent = orig; btn.style.color = ''; }, 2000);
    } catch {
        // Fallback
        const ta = document.createElement('textarea');
        ta.value = text;
        ta.style.cssText = 'position:fixed;opacity:0';
        document.body.appendChild(ta);
        ta.select();
        document.execCommand('copy');
        document.body.removeChild(ta);
    }
}

/* ══ UTILS ════════════════════════════════════════════════════ */
function escapeHtml(s) {
    return String(s)
        .replace(/&/g, '&amp;').replace(/</g, '&lt;')
        .replace(/>/g, '&gt;').replace(/"/g, '&quot;');
}

/* ══ EVENT LISTENERS ══════════════════════════════════════════ */
generateBtn.addEventListener('click', generateImage);

promptInput.addEventListener('keydown', e => {
    if ((e.ctrlKey || e.metaKey) && e.key === 'Enter') {
        e.preventDefault();
        generateImage();
    }
});

/* ══ INIT ═════════════════════════════════════════════════════ */
(function init() {
    // Restore gallery count
    galleryCount.textContent = `${gallery.length} image${gallery.length !== 1 ? 's' : ''}`;
    // Focus prompt on load
    promptInput.focus();
})();