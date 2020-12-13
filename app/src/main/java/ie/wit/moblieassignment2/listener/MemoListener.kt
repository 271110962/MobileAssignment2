package ie.wit.moblieassignment2.listener

import ie.wit.moblieassignment2.models.MemoModel

interface MemoListener {
    fun onMemoClick(memo: MemoModel)
}