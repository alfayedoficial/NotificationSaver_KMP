package com.afapps.managenotification.core.utilities

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import app.cash.paging.compose.LazyPagingItems

fun <T : Any> LazyListScope.items(
    items: LazyPagingItems<T>,
    key: ((item: T) -> Any)? = null,
    itemContent: @Composable (item: T?) -> Unit
) {
    items(
        count = items.itemCount,
        key = if (key != null) { index ->
            val item = items.peek(index)
            if (item != null) key(item) else index
        } else null
    ) { index ->
        itemContent(items[index])
    }
}


fun <T : Any> LazyListScope.itemsIndexed(
    items: LazyPagingItems<T>,
    key: ((index: Int, item: T?) -> Any)? = null,
    itemContent: @Composable (index: Int, item: T?) -> Unit
) {
    items(
        count = items.itemCount,
        key = if (key != null) { index ->
            val item = items.peek(index)
            key(index, item)
        } else null
    ) { index ->
        itemContent(index, items[index])
    }
}
