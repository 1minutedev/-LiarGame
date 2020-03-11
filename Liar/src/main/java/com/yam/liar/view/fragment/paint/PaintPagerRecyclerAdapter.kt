package com.yam.liar.view.fragment.paint

import android.content.Context
import android.graphics.*
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import com.yam.core.util.RUtil
import kotlinx.android.synthetic.main.item_pager.view.*
import org.json.JSONObject

class PaintPagerRecyclerAdapter : RecyclerView.Adapter<PagerViewHolder> {
    var context: Context
    var total: Int = 3
    lateinit var fragment: PaintFragment
    open var holders: ArrayList<PagerViewHolder>

    constructor(context: Context, total: Int) {
        this.context = context
        this.total = total
        holders = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(RUtil.getLayoutR(context, "item_pager" ), parent, false)
        var holder = PagerViewHolder(view)
        holders.add(holder)
        return holder
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.context = context
        holder.fragment = fragment
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return total
    }
}

class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    lateinit var context: Context
    lateinit var fragment: PaintFragment

    var seq = 0

    lateinit var tvSeq: TextView

    lateinit var btnClose: ImageButton

    /**
     * 색상 변경 버튼
     */
    lateinit var llColor: LinearLayout
    lateinit var ivColor: ImageView
    lateinit var tvColor: TextView

    /**
     * 선 굵기 변경 버튼
     */
    lateinit var llLine: LinearLayout
    lateinit var ivLine: View
    lateinit var tvLine: TextView

    /**
     * 팔레트 설정
     */
    lateinit var llPalette: LinearLayout
    lateinit var llPalette1: LinearLayout
    lateinit var llPalette2: LinearLayout

    companion object{
        @ColorInt
        var color: Int = Color.parseColor("#000000")
    }

    lateinit var paletteColors: Array<Int>
    var ivColors = arrayOfNulls<ImageView>(9)

    /**
     * 선 굵기 설정
     */
    lateinit var llLineSetting: LinearLayout
    lateinit var lineSizeSeekBar: SeekBar

    var currentLineSize: Int = 0

    /**
     * 그림판 영역 설정
     */
    lateinit var llPaint: LinearLayout

    lateinit var mPaint: Paint

    lateinit var mPaintView: PaintView

    fun bind(position: Int) {
        this.seq = position + 1

        init()
    }

    fun init() {
        tvSeq = itemView.tv_seq

        btnClose = itemView.btn_close

        llColor = itemView.ll_color_setting_click
        tvColor = itemView.tv_color_select
        ivColor = itemView.iv_cir

        llLine = itemView.ll_line_setting_click
        tvLine = itemView.tv_line_size
        ivLine = itemView.iv_line

        llPalette = itemView.ll_palette
        llPalette1 = itemView.ll_palette1
        llPalette2 = itemView.ll_palette2

        llLineSetting = itemView.ll_line_setting
        lineSizeSeekBar = itemView.sb_line_size

        tvSeq.setText(seq.toString())

        var circle = ShapeDrawable(OvalShape())
        circle.paint.setColor(color)
        var drawableCir = LayerDrawable(arrayOf(circle))
        drawableCir.setLayerInset(0, 10, 10, 10, 10)
        ivColor.background = drawableCir

        setPalette()

        ivLine.setBackgroundColor(Color.BLACK)
        setLineHeight(getDp(1))

        llPaint = itemView.ll_paint
        mPaintView = PaintView(context)

        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.isDither = true
        mPaint.color = color
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeJoin = Paint.Join.ROUND
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.strokeWidth = currentLineSize + 0.0f
        llPaint.addView(mPaintView)

        setEvent()
    }

    fun setEvent() {
        btnClose.setOnClickListener(this)

        llColor.setOnClickListener(this)
        ivColor.setOnClickListener(this)
        tvColor.setOnClickListener(this)

        llLine.setOnClickListener(this)
        ivLine.setOnClickListener(this)
        tvLine.setOnClickListener(this)

        lineSizeSeekBar.setOnSeekBarChangeListener(this)
    }

    fun setPalette() {
        var cnt = 0
        val size = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25f, context.getResources().getDisplayMetrics()).toInt()
        val margin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, context.getResources().getDisplayMetrics()).toInt()

        paletteColors = arrayOf(Color.parseColor("#000000"), Color.parseColor("#FF5675"), Color.parseColor("#FFC300"), Color.parseColor("#FFFA78"), Color.parseColor("#63CC63")
        , Color.parseColor("#FFFFFF"), Color.parseColor("#6180FA"), Color.parseColor("#3643B3"), Color.parseColor("#9B6CC9"))

        for(color in paletteColors){
            var circle = ShapeDrawable(OvalShape())
            circle.paint.setColor(color)

            var drawableCir = LayerDrawable(arrayOf(circle))
            drawableCir.setLayerInset(0, 10, 10, 10, 10)

            var params = LinearLayout.LayoutParams(size, size)
            params.leftMargin = margin
            params.topMargin = if(cnt < 5) margin else 0

            var imageView = ImageView(context)
            imageView.background = drawableCir

            imageView.layoutParams = params

            ivColors!![cnt] = imageView

            if(cnt < 5) {
                llPalette1.addView(imageView)
            } else {
                llPalette2.addView(imageView)
            }

            imageView.setOnClickListener(this)

            cnt++
        }
    }

    fun selectColor(index: Int){
        var drawable = (ivColor.background as LayerDrawable)
        var circle = (drawable.getDrawable(0) as ShapeDrawable)
        circle.paint.setColor(paletteColors[index])

        var drawableCir = LayerDrawable(arrayOf(circle))
        drawableCir.setLayerInset(0, 10, 10, 10, 10)

        ivColor.background = drawableCir
    }

    fun getDp(value: Int) : Int{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.0f + value, context.getResources().getDisplayMetrics()).toInt()
    }

    fun setLineHeight(height: Int) {
        currentLineSize = height

        var params = ivLine.layoutParams

        if(currentLineSize < 1){
            currentLineSize = 1
        }

        params.height = currentLineSize

        ivLine.layoutParams = params
    }

    open fun hideButton(){
        if(llLine.visibility == View.VISIBLE) {
            llLine.visibility = View.GONE
        }
        if(llColor.visibility == View.VISIBLE) {
            llColor.visibility = View.GONE
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            llColor, ivColor, tvColor -> {
                if(llPalette.visibility == View.GONE){
                    llPalette.visibility = View.VISIBLE
                }
                if(llLineSetting.visibility == View.VISIBLE){
                    llLineSetting.visibility = View.GONE
                }
            }
            llLine, ivLine, tvLine -> {
                if(llLineSetting.visibility == View.GONE){
                    llLineSetting.visibility = View.VISIBLE
                }
                if(llPalette.visibility == View.VISIBLE){
                    llPalette.visibility = View.GONE
                }
            }
            ivColors[0], ivColors[1], ivColors[2], ivColors[3], ivColors[4],
            ivColors[5], ivColors[6], ivColors[7], ivColors[8] -> {
                for(index in ivColors.indices){
                    if(view == ivColors[index]){
                        selectColor(index)
                        color = paletteColors[index]

                        mPaint.setColor(color)
                    }
                }
            }
            btnClose -> {
                fragment.activity!!.onBackPressed()

                var resultData = JSONObject()
                resultData.put("result", true)
                fragment.sendCallback(resultData)
            }
        }
    }

    override fun onProgressChanged(seekBar: SeekBar?, value: Int, fromUser: Boolean) {
        if(seekBar == lineSizeSeekBar) {
            setLineHeight(getDp(value))
            mPaint.strokeWidth = currentLineSize + 0.0f
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

    inner class PaintView : View {
        private var mBitmap: Bitmap? = null
        private var mCanvas: Canvas? = null
        private var mPath: Path? = null
        private var mBitmapPaint: Paint? = null
        private var mX: Float = 0.toFloat()
        private var mY: Float = 0.toFloat()

        private val TOUCH_TOLERANCE = 4f

        constructor(c: Context) : super(c) {
            mPath = Path()
            mBitmapPaint = Paint(Paint.DITHER_FLAG)
        }

        constructor(c: Context, att: AttributeSet) : super(c, att) {
            mPath = Path()
            mBitmapPaint = Paint(Paint.DITHER_FLAG)
        }

        constructor(c: Context, att: AttributeSet, ref: Int) : super(c, att, ref) {
            mPath = Path()
            mBitmapPaint = Paint(Paint.DITHER_FLAG)
        }

        override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
            super.onSizeChanged(w, h, oldw, oldh)
            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            mCanvas = Canvas(mBitmap!!)
        }

        override fun onDraw(canvas: Canvas) {
            canvas.drawColor(Color.WHITE)
            canvas.drawBitmap(mBitmap!!, 0f, 0f, mBitmapPaint)
            canvas.drawPath(mPath!!, mPaint)
        }

        fun canvasClear(){
            mCanvas!!.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
            invalidate()
        }

        private fun touch_start(x: Float, y: Float) {
            if(llLineSetting.visibility == View.VISIBLE){
                llLineSetting.visibility = View.GONE
            }
            if(llPalette.visibility == View.VISIBLE){
                llPalette.visibility = View.GONE
            }

            mPath!!.reset()
            mPath!!.moveTo(x, y)
            mX = x
            mY = y
        }

        private fun touch_move(x: Float, y: Float) {
            val dx = Math.abs(x - mX)
            val dy = Math.abs(y - mY)
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                mPath!!.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
                mX = x
                mY = y
            }
        }

        private fun touch_up() {
            mPath!!.lineTo(mX, mY)
            mCanvas!!.drawPath(mPath!!, mPaint)
            mPath!!.reset()
        }

        override fun onTouchEvent(event: MotionEvent): Boolean {
            if(!PaintFragment.isReadOnly) {
                val x = event.x
                val y = event.y
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        touch_start(x, y)
                        invalidate()
                    }
                    MotionEvent.ACTION_MOVE -> {
                        touch_move(x, y)
                        invalidate()
                    }
                    MotionEvent.ACTION_UP -> {
                        touch_up()
                        invalidate()
                    }
                }
            }
            return true
        }
    }
}