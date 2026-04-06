/**
 * Visionaire — Text-to-Image Generator
 * Frontend script: handles user interaction, API calls, and UI state.
 */

/* ═══════════════════════════════════════════════════════════════
   DOM References
═══════════════════════════════════════════════════════════════ */
const promptInput    = document.getElementById('prompt');
const styleSelect    = document.getElementById('style');
const sizeSelect     = document.getElementById('size');
const generateBtn    = document.getElementById('generateBtn');
const errorBanner    = document.getElementById('errorBanner');
const errorMessage   = document.getElementById('errorMessage');
const closeError     = document.getElementById('closeError');
const loadingState   = document.getElementById('loadingState');
const loadingStep    = document.getElementById('loadingStep');
const resultSection  = document.getElementById('resultSection');
const generatedImage = document.getElementById('generatedImage');
const downloadBtn    = document.getElementById('downloadBtn');
const copyUrlBtn     = document.getElementById('copyUrlBtn');
const resultMeta     = document.getElementById('resultMeta');
const revisedPromptBox  = document.getElementById('revisedPromptBox');
const revisedPromptText = document.getElementById('revisedPromptText');
const charCount      = document.getElementById('charCount');

/* ═══════════════════════════════════════════════════════════════
   Config
═══════════════════════════════════════════════════════════════ */
const API_ENDPOINT = '/api/generate-image';

/** Loading step messages — cycled during the API call */
const LOADING_STEPS = [
    'Initializing DALL·E 3...',
    'Parsing your prompt...',
    'Composing the scene...',
    'Rendering high-resolution pixels...',
    'Applying quality enhancement...',
    'Finalizing your image...',
];

/* ═══════════════════════════════════════════════════════════════
   Grain Canvas — Animated Film-Grain Overlay
═══════════════════════════════════════════════════════════════ */
(function initGrain() {
    const canvas = document.getElementById('grain');
    const ctx    = canvas.getContext('2d');
    let   frame  = 0;

    function resize() {
        canvas.width  = window.innerWidth;
        canvas.height = window.innerHeight;
    }

    function drawGrain() {
        const { width, height } = canvas;
        const imageData = ctx.createImageData(width, height);
        const data      = imageData.data;

        for (let i = 0; i < data.length; i += 4) {
            const v = Math.random() * 255 | 0;
            data[i] = data[i + 1] = data[i + 2] = v;
            data[i + 3] = 18; // very low alpha — subtle
        }

        ctx.putImageData(imageData, 0, 0);
        frame++;
        // Only refresh every 3 frames to reduce CPU usage
        if (frame % 3 === 0) requestAnimationFrame(drawGrain);
        else setTimeout(() => requestAnimationFrame(drawGrain), 80);
    }

    resize();
    window.addEventListener('resize', resize);
    drawGrain();
})();

/* ═══════════════════════════════════════════════════════════════
   Character Counter
═══════════════════════════════════════════════════════════════ */
promptInput.addEventListener('input', () => {
    const len = promptInput.value.length;
    charCount.textContent = len;
    charCount.style.color = len > 3800 ? '#ff8080' : '';
});

/* ═══════════════════════════════════════════════════════════════
   Loading Step Cycler
═══════════════════════════════════════════════════════════════ */
let loadingInterval = null;

function startLoadingSteps() {
    let stepIdx = 0;
    loadingStep.textContent = LOADING_STEPS[0];

    loadingInterval = setInterval(() => {
        stepIdx = (stepIdx + 1) % LOADING_STEPS.length;
        loadingStep.style.animation = 'none';
        // Force reflow to restart animation
        void loadingStep.offsetWidth;
        loadingStep.style.animation = '';
        loadingStep.textContent = LOADING_STEPS[stepIdx];
    }, 2400);
}

function stopLoadingSteps() {
    if (loadingInterval) {
        clearInterval(loadingInterval);
        loadingInterval = null;
    }
}

/* ═══════════════════════════════════════════════════════════════
   UI State Machine
═══════════════════════════════════════════════════════════════ */
function setState(state) {
    switch (state) {
        case 'idle':
            generateBtn.disabled = false;
            generateBtn.querySelector('.btn-text').textContent = 'GENERATE IMAGE';
            hideElement(loadingState);
            break;

        case 'loading':
            generateBtn.disabled = true;
            generateBtn.querySelector('.btn-text').textContent = 'GENERATING...';
            hideElement(errorBanner);
            hideElement(resultSection);
            showElement(loadingState);
            startLoadingSteps();
            break;

        case 'success':
            generateBtn.disabled = false;
            generateBtn.querySelector('.btn-text').textContent = 'GENERATE ANOTHER';
            hideElement(loadingState);
            stopLoadingSteps();
            showElement(resultSection);
            break;

        case 'error':
            generateBtn.disabled = false;
            generateBtn.querySelector('.btn-text').textContent = 'GENERATE IMAGE';
            hideElement(loadingState);
            stopLoadingSteps();
            showElement(errorBanner);
            break;
    }
}

function showElement(el) { el.hidden = false; }
function hideElement(el) { el.hidden = true; }

/* ═══════════════════════════════════════════════════════════════
   Error Handling
═══════════════════════════════════════════════════════════════ */
function showError(message) {
    errorMessage.textContent = message;
    setState('error');
    errorBanner.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
}

closeError.addEventListener('click', () => hideElement(errorBanner));

/* ═══════════════════════════════════════════════════════════════
   Image Generation — Main Flow
═══════════════════════════════════════════════════════════════ */
async function generateImage() {
    const prompt = promptInput.value.trim();

    // ── Client-side validation ──────────────────────────────────
    if (!prompt) {
        showError('Please enter a prompt before generating an image.');
        promptInput.focus();
        return;
    }

    if (prompt.length < 3) {
        showError('Your prompt is too short. Please describe your image in more detail.');
        promptInput.focus();
        return;
    }

    setState('loading');
    loadingState.scrollIntoView({ behavior: 'smooth', block: 'nearest' });

    const requestBody = {
        prompt: prompt,
        style:  styleSelect.value || null,
        size:   sizeSelect.value || null,
    };

    try {
        const response = await fetch(API_ENDPOINT, {
            method:  'POST',
            headers: { 'Content-Type': 'application/json' },
            body:    JSON.stringify(requestBody),
        });

        const data = await response.json();

        if (!response.ok) {
            // Handle structured API error responses
            const message = buildErrorMessage(data, response.status);
            showError(message);
            return;
        }

        // ── Success ──────────────────────────────────────────────
        displayResult(data);
        setState('success');

    } catch (err) {
        console.error('Network or parse error:', err);
        showError('Unable to connect to the server. Please check your connection and try again.');
    }
}

/**
 * Builds a user-friendly error message from the API error response.
 */
function buildErrorMessage(data, httpStatus) {
    // Field validation errors
    if (data.fieldErrors && data.fieldErrors.length > 0) {
        return data.fieldErrors.map(fe => `${fe.field}: ${fe.message}`).join(' · ');
    }

    // Known error codes
    if (data.error === 'OPENAI_API_ERROR') {
        return `OpenAI API Error: ${data.message}`;
    }

    // Rate limit
    if (httpStatus === 429) {
        return 'Rate limit reached. Please wait a moment and try again.';
    }

    return data.message || `Request failed (${httpStatus}). Please try again.`;
}

/* ═══════════════════════════════════════════════════════════════
   Display Result
═══════════════════════════════════════════════════════════════ */
function displayResult(data) {
    // ── Main Image ──────────────────────────────────────────────
    generatedImage.src = data.imageUrl;
    generatedImage.alt = `AI generated image: ${promptInput.value.trim().substring(0, 80)}`;

    // Preload to ensure smooth reveal animation
    generatedImage.onload = () => {
        generatedImage.style.animation = 'none';
        void generatedImage.offsetWidth;
        generatedImage.style.animation = '';
    };

    // ── Download & Copy buttons ─────────────────────────────────
    downloadBtn.href = data.imageUrl;
    downloadBtn.setAttribute('download', `visionaire-${Date.now()}.png`);

    copyUrlBtn.onclick = () => copyToClipboard(data.imageUrl);

    // ── Metadata ────────────────────────────────────────────────
    const sizeLabel = data.size || sizeSelect.value;
    const model     = data.model || 'dall-e-3';
    const ts        = data.generatedAt
        ? new Date(data.generatedAt).toLocaleTimeString()
        : new Date().toLocaleTimeString();

    resultMeta.textContent = `${model.toUpperCase()} · ${sizeLabel} · ${ts}`;

    // ── Revised Prompt ──────────────────────────────────────────
    if (data.revisedPrompt && data.revisedPrompt !== promptInput.value.trim()) {
        revisedPromptText.textContent = data.revisedPrompt;
        showElement(revisedPromptBox);
    } else {
        hideElement(revisedPromptBox);
    }

    // Scroll to result
    setTimeout(() => {
        resultSection.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }, 200);
}

/* ═══════════════════════════════════════════════════════════════
   Clipboard Copy
═══════════════════════════════════════════════════════════════ */
async function copyToClipboard(text) {
    try {
        await navigator.clipboard.writeText(text);
        const original = copyUrlBtn.textContent;
        copyUrlBtn.textContent = '✓ COPIED';
        copyUrlBtn.style.color = '#44ff88';
        setTimeout(() => {
            copyUrlBtn.textContent = original;
            copyUrlBtn.style.color = '';
        }, 2000);
    } catch {
        // Fallback for older browsers
        const ta = document.createElement('textarea');
        ta.value = text;
        ta.style.position = 'fixed';
        ta.style.opacity  = '0';
        document.body.appendChild(ta);
        ta.select();
        document.execCommand('copy');
        document.body.removeChild(ta);
    }
}

/* ═══════════════════════════════════════════════════════════════
   Event Listeners
═══════════════════════════════════════════════════════════════ */
generateBtn.addEventListener('click', generateImage);

// Allow Ctrl+Enter / Cmd+Enter to trigger generation from the textarea
promptInput.addEventListener('keydown', (e) => {
    if ((e.ctrlKey || e.metaKey) && e.key === 'Enter') {
        e.preventDefault();
        generateImage();
    }
});

// Quick prompt suggestions on triple-click label (UX easter egg)
document.querySelector('.hero-title').addEventListener('dblclick', () => {
    const suggestions = [
        'A majestic snow leopard perched on a Himalayan peak at dusk, mist swirling around granite cliffs, cinematic lighting',
        'An enchanted library floating in deep space, bookshelves spiraling infinitely, warm golden light emanating from ancient tomes',
        'A micro-city inside a terrarium, tiny lights glowing at night, rain droplets on glass, tilt-shift photography',
        'A samurai meditating beneath a sakura tree during a lightning storm, ink wash painting meets hyper-realism',
        'Deep ocean bioluminescent jellyfish ballet, dark water, 8K macro photography, ethereal blue glow',
    ];
    const idx = Math.floor(Math.random() * suggestions.length);
    promptInput.value = suggestions[idx];
    charCount.textContent = promptInput.value.length;
    promptInput.focus();
    promptInput.scrollIntoView({ behavior: 'smooth', block: 'center' });
});